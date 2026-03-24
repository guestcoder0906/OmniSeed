# Stage 1: Build Java Runner
FROM openjdk:21-slim AS java-builder
WORKDIR /app
COPY . .
WORKDIR /app/java-runner
RUN chmod +x build.sh && bash build.sh

# Stage 2: Final Runtime
FROM openjdk:21-slim

# Install Node.js 20
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Build Cache Bust (Hotfix v6.0)
ENV BUILD_ID=20260324-0811

# Copy all project files
COPY . .

# Copy compiled classes and libraries from java-builder
COPY --from=java-builder /app/java-runner/build /app/java-runner/build
COPY --from=java-builder /app/java-runner/libs /app/java-runner/libs

# Expose Hugging Face Port
EXPOSE 7860

# Start scanning engine
CMD ["npm", "start"]
