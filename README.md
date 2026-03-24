---
title: OmniSeed
emoji: 🌍
colorFrom: blue
colorTo: green
sdk: docker
pinned: false
last_updated: 2026-03-24T08:00:00Z
---

# 🌍 OmniSeed: Unified Multi-Engine Minecraft Seed Finder

The ultimate high-performance platform for Minecraft seed finding, integrating **Cubiomes**, **SeedLocator**, and **SeedFinding Org** logic into a single, voxel-accurate web interface.

### 🚀 Key Features
*   **Omni-Chunk Voxel Parser**: Match arbitrary 3D block patterns via custom mathematical syntax.
*   **Structure Piece Generator**: Enforce constraints on internal structure layouts (e.g. Min 2 Blacksmiths, Specific Mansion Rooms, Igloo Basements).
*   **Loot Prediction**: Predict exact chest contents and archaeology loot across all major versions.
*   **Multi-Engine Routing**: Automatically chooses the fastest algorithm (C# logic vs Java deep scan) based on your parameters.
*   **v3.4 Stable (v13.4 Java)**: Improved Village Jigsaw accuracy, added **Structure Clusters**, **Clustered Biomes**, and improved **Strict Loot Filtering** with precise enchantment matching.

### 🛠 Deployment
This Space runs via a custom **Docker** container (OpenJDK 21 + Node.js 20). 

### 📜 Credits
Integrating powerful open-source logic from:
*   [Cubiomes](https://github.com/Cubitect/cubiomes)
*   [SeedFinding Org](https://github.com/SeedFinding)
*   [SeedLocator](https://github.com/acheong08/MC-SeedLocator)
