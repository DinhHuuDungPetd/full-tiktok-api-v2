# TailAdmin Next.js - Free Next.js Tailwind Admin Dashboard Template

TailAdmin is a free and open-source admin dashboard template built on **Next.js and Tailwind CSS** providing developers with everything they need to create a feature-rich and data-driven: back-end, dashboard, or admin panel solution for any sort of web project.

![TailAdmin - Next.js Dashboard Preview](./banner.png)

With TailAdmin Next.js, you get access to all the necessary dashboard UI components, elements, and pages required to build a high-quality and complete dashboard or admin panel. Whether you're building a dashboard or admin panel for a complex web application or a simple website. 

TailAdmin utilizes the powerful features of **Next.js 15** and common features of Next.js such as server-side rendering (SSR), static site generation (SSG), and seamless API route integration. Combined with the advancements of **React 19** and the robustness of **TypeScript**, TailAdmin is the perfect solution to help get your project up and running quickly.

## Overview

TailAdmin provides essential UI components and layouts for building feature-rich, data-driven admin dashboards and control panels. It's built on:

- Next.js 15.x
- React 19
- TypeScript
- Tailwind CSS V4

### Quick Links
- [✨ Visit Website](https://tailadmin.com)
- [📄 Documentation](https://tailadmin.com/docs)
- [⬇️ Download](https://tailadmin.com/download)
- [🖌️ Figma Design File (Community Edition)](https://www.figma.com/community/file/1463141366275764364)
- [⚡ Get PRO Version](https://tailadmin.com/pricing)

### Demos
- [Free Version](https://nextjs-free-demo.tailadmin.com)
- [Pro Version](https://nextjs-demo.tailadmin.com)

### Other Versions
- [HTML Version](https://github.com/TailAdmin/tailadmin-free-tailwind-dashboard-template)
- [React Version](https://github.com/TailAdmin/free-react-tailwind-admin-dashboard)
- [Vue.js Version](https://github.com/TailAdmin/vue-tailwind-admin-dashboard)

## Installation

### Prerequisites
To get started with TailAdmin, ensure you have the following prerequisites installed and set up:

- Node.js 18.x or later (recommended to use Node.js 20.x or later)

### Cloning the Repository
Clone the repository using the following command:

```bash
git clone https://github.com/TailAdmin/free-nextjs-admin-dashboard.git
```

> Windows Users: place the repository near the root of your drive if you face issues while cloning.

1. Install dependencies:
    ```bash
    npm install
    # or
    yarn install
    ```
    > Use `--legacy-peer-deps` flag if you face peer-dependency error during installation.

2. Start the development server:
    ```bash
    npm run dev
    # or
    yarn dev
    ```

## Docker Build and Run

### Prerequisites for Docker
- Docker installed on your system
- Docker Compose (usually comes with Docker Desktop)

### Building and Running with Docker

1. **Build the Docker image:**
   ```bash
   docker build -t tailadmin-nextjs .
   ```

2. **Run the container:**
   ```bash
   docker run -p 3000:3000 tailadmin-nextjs
   ```

3. **Or use Docker Compose (recommended):**
   
   Create a `docker-compose.yml` file in the root directory:
   ```yaml
   version: '3.8'
   services:
     frontend:
       build: .
       ports:
         - "3000:3000"
       environment:
         - NODE_ENV=production
       volumes:
         - ./public:/app/public
       restart: unless-stopped
   ```

   Then run:
   ```bash
   docker-compose up -d
   ```

4. **Development mode with Docker:**
   ```bash
   # Build for development
   docker build -t tailadmin-nextjs:dev --target development .
   
   # Run in development mode
   docker run -p 3000:3000 -v $(pwd):/app -v /app/node_modules tailadmin-nextjs:dev
   ```

### Dockerfile

Create a `Dockerfile` in the root directory:

```dockerfile
# Use the official Node.js runtime as the base image
FROM node:20-alpine AS base

# Install dependencies only when needed
FROM base AS deps
RUN apk add --no-cache libc6-compat
WORKDIR /app

# Install dependencies based on the preferred package manager
COPY package.json package-lock.json* ./
RUN npm ci --only=production

# Rebuild the source code only when needed
FROM base AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .

# Next.js collects completely anonymous telemetry data about general usage.
# Learn more here: https://nextjs.org/telemetry
# Uncomment the following line in case you want to disable telemetry during the build.
ENV NEXT_TELEMETRY_DISABLED 1

RUN npm run build

# Production image, copy all the files and run next
FROM base AS runner
WORKDIR /app

ENV NODE_ENV production
ENV NEXT_TELEMETRY_DISABLED 1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

COPY --from=builder /app/public ./public

# Set the correct permission for prerender cache
RUN mkdir .next
RUN chown nextjs:nodejs .next

# Automatically leverage output traces to reduce image size
# https://nextjs.org/docs/advanced-features/output-file-tracing
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static

USER nextjs

EXPOSE 3000

ENV PORT 3000
ENV HOSTNAME "0.0.0.0"

CMD ["node", "server.js"]
```

### Multi-stage Dockerfile for Development

For development with hot reload:

```dockerfile
# Development stage
FROM node:20-alpine AS development
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev"]

# Production stage
FROM node:20-alpine AS production
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm ci --only=production
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
```

### Docker Commands Reference

```bash
# Build image
docker build -t tailadmin-nextjs .

# Run container
docker run -p 3000:3000 tailadmin-nextjs

# Run in detached mode
docker run -d -p 3000:3000 tailadmin-nextjs

# View running containers
docker ps

# Stop container
docker stop <container_id>

# Remove container
docker rm <container_id>

# Remove image
docker rmi tailadmin-nextjs

# View logs
docker logs <container_id>

# Execute commands in running container
docker exec -it <container_id> /bin/sh
```

### Environment Variables

You can pass environment variables to the Docker container:

```bash
docker run -p 3000:3000 -e NODE_ENV=production -e NEXT_PUBLIC_API_URL=https://api.example.com tailadmin-nextjs
```

The application will be available at `http://localhost:3000` after running the Docker commands.

## Components

TailAdmin is a pre-designed starting point for building a web-based dashboard using Next.js and Tailwind CSS. The template includes:

- Sophisticated and accessible sidebar
- Data visualization components
- Profile management and custom 404 page
- Tables and Charts(Line and Bar)
- Authentication forms and input elements
- Alerts, Dropdowns, Modals, Buttons and more
- Can't forget Dark Mode 🕶️

All components are built with React and styled using Tailwind CSS for easy customization.

## Feature Comparison

### Free Version
- 1 Unique Dashboard
- 30+ dashboard components
- 50+ UI elements
- Basic Figma design files
- Community support

### Pro Version
- 5 Unique Dashboards: Analytics, Ecommerce, Marketing, CRM, Stocks (more coming soon)
- 400+ dashboard components and UI elements
- Complete Figma design file
- Email support

To learn more about pro version features and pricing, visit our [pricing page](https://tailadmin.com/pricing).

## Changelog

### Version 2.0.2 - [March 25, 2025]

- Upgraded to Next v15.2.3 for [CVE-2025-29927](https://nextjs.org/blog/cve-2025-29927) concerns
- Included overrides vectormap for packages to prevent peer dependency errors during installation.
- Migrated from react-flatpickr to flatpickr package for React 19 support

### Version 2.0.1 - [February 27, 2025]

#### Update Overview

- Upgraded to Tailwind CSS v4 for better performance and efficiency.
- Updated class usage to match the latest syntax and features.
- Replaced deprecated class and optimized styles.

#### Next Steps

- Run npm install or yarn install to update dependencies.
- Check for any style changes or compatibility issues.
- Refer to the Tailwind CSS v4 [Migration Guide](https://tailwindcss.com/docs/upgrade-guide) on this release. if needed.
- This update keeps the project up to date with the latest Tailwind improvements. 🚀

### v2.0.0 (February 2025)
A major update focused on Next.js 15 implementation and comprehensive redesign.

#### Major Improvements
- Complete redesign using Next.js 15 App Router and React Server Components
- Enhanced user interface with Next.js-optimized components
- Improved responsiveness and accessibility
- New features including collapsible sidebar, chat screens, and calendar
- Redesigned authentication using Next.js App Router and server actions
- Updated data visualization using ApexCharts for React

#### Breaking Changes

- Migrated from Next.js 14 to Next.js 15
- Chart components now use ApexCharts for React
- Authentication flow updated to use Server Actions and middleware

[Read more](https://tailadmin.com/docs/update-logs/nextjs) on this release.

#### Breaking Changes
- Migrated from Next.js 14 to Next.js 15
- Chart components now use ApexCharts for React
- Authentication flow updated to use Server Actions and middleware

### v1.3.4 (July 01, 2024)
- Fixed JSvectormap rendering issues

### v1.3.3 (June 20, 2024)
- Fixed build error related to Loader component

### v1.3.2 (June 19, 2024)
- Added ClickOutside component for dropdown menus
- Refactored sidebar components
- Updated Jsvectormap package

### v1.3.1 (Feb 12, 2024)
- Fixed layout naming consistency
- Updated styles

### v1.3.0 (Feb 05, 2024)
- Upgraded to Next.js 14
- Added Flatpickr integration
- Improved form elements
- Enhanced multiselect functionality
- Added default layout component

## License

TailAdmin Next.js Free Version is released under the MIT License.

## Support

If you find this project helpful, please consider giving it a star on GitHub. Your support helps us continue developing and maintaining this template.
