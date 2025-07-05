# Use Node.js 20 Alpine
FROM node:20-alpine

# Set working directory
WORKDIR /app

# 1. Copy package files first (for better caching)
COPY package*.json ./
RUN npm ci

# 2. Copy source code
COPY . .

# 3. Build the application
ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build

# Expose port
EXPOSE 3000

# 4. Start the application
CMD ["npm", "start"]