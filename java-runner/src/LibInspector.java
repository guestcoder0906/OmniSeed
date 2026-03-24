import com.seedfinding.mcfeature.structure.generator.structure.VillageGenerator;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.MCLootTables;
import com.seedfinding.mcfeature.loot.LootTable;
import java.util.*;

public class LibInspector {
    public static void main(String[] args) throws Exception {
        applyPatches();
        MCVersion version = MCVersion.v1_18;
        
        System.out.println("Scanning 1000 seeds for ANY loot positions...");
        int seedsWithLoot = 0;
        int totalChests = 0;
        for (long seed = 0; seed < 1000; seed++) {
            com.seedfinding.mcbiome.source.OverworldBiomeSource src = new com.seedfinding.mcbiome.source.OverworldBiomeSource(version, seed);
            com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator terrain = new com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator(src);
            com.seedfinding.mccore.rand.ChunkRand rand = new com.seedfinding.mccore.rand.ChunkRand();
            VillageGenerator gen = new VillageGenerator(version);
            
            try {
                gen.generate(terrain, 0, 0, rand);
                var loot = gen.getLootPos();
                if (loot != null && !loot.isEmpty()) {
                    seedsWithLoot++;
                    totalChests += loot.size();
                    for (var p : loot) {
                         // System.out.println("Found chest at " + p.getSecond());
                    }
                }
            } catch (Throwable e) {}
        }
        System.out.println("Finished. Seeds with loot: " + seedsWithLoot + "/1000, Total chests: " + totalChests);
    }

    static void applyPatches() throws Exception {
        var desertPool = VillageGenerator.STARTS.get(VillageGenerator.VillageType.DESERT);
        for (var vt : VillageGenerator.VillageType.values()) {
            if (VillageGenerator.STARTS.get(vt) == null) VillageGenerator.STARTS.put(vt, desertPool);
        }
        var desertBlocks = VillageGenerator.VillageType.DESERT.getJigsawBlocks();
        for (var vt : VillageGenerator.VillageType.values()) {
            if (vt == VillageGenerator.VillageType.DESERT) continue;
            var blocks = vt.getJigsawBlocks();
            if (blocks != null) blocks.putAll(desertBlocks);
        }
        var emptyList = new ArrayList<com.seedfinding.mccore.util.data.Pair<String, Integer>>();
        var emptyTriplet = new com.seedfinding.mccore.util.data.Triplet<String, List<com.seedfinding.mccore.util.data.Pair<String, Integer>>, Object>("empty", emptyList, null);
        Map<String, Object> poolMap = (Map) VillageGenerator.VILLAGE_POOLS;
        Deque<String> toCheck = new ArrayDeque<>();
        for (Object key : poolMap.keySet()) if (key instanceof String) toCheck.add((String)key);
        Set<String> visited = new HashSet<>(toCheck);
        while (!toCheck.isEmpty()) {
            String current = toCheck.poll();
            Object val = poolMap.get(current);
            if (val instanceof com.seedfinding.mccore.util.data.Triplet) {
                com.seedfinding.mccore.util.data.Triplet t = (com.seedfinding.mccore.util.data.Triplet) val;
                String fallback = (String) t.getFirst();
                if (fallback != null && !fallback.isEmpty() && !fallback.equals("empty")) {
                    if (!poolMap.containsKey(fallback)) poolMap.put(fallback, emptyTriplet);
                    if (visited.add(fallback)) toCheck.add(fallback);
                }
                List<com.seedfinding.mccore.util.data.Pair<String, Integer>> pieces = (List<com.seedfinding.mccore.util.data.Pair<String, Integer>>) t.getSecond();
                if (pieces != null) {
                    for (var p : pieces) {
                        String ref = p.getFirst();
                        if (ref != null && !ref.isEmpty()) {
                            if (!poolMap.containsKey(ref)) poolMap.put(ref, emptyTriplet);
                            if (visited.add(ref)) toCheck.add(ref);
                        }
                    }
                } else { poolMap.put(current, emptyTriplet); }
            }
        }
    }
}
