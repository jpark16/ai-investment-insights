# ai-investment-insights
🚀 AI-Powered Portfolio Analytics Platform

A Spring Boot backend that analyzes investment portfolios, computes risk and allocation metrics, and generates AI-driven investment insights using the OpenAI API.

📌 Overview

This project simulates how modern investment platforms analyze portfolios and provide decision-support insights.

It combines:

📊 Financial analytics (allocation, risk, sector exposure)
🔄 Data pipeline (market data refresh)
🤖 AI integration (OpenAI API for natural-language insights)
🧠 Features
✅ Portfolio Management
Add and store assets via REST API
Track symbol, quantity, price, and sector
📊 Analytics Engine
Total portfolio value calculation
Asset allocation (% by holding)
Sector allocation (% by industry)
Concentration risk detection
🔄 Data Pipeline
Simulated market data ingestion
Refresh asset prices dynamically

Pipeline flow:

external data → update database → analytics → insights
🤖 AI-Powered Insights
Integrates OpenAI API
Converts portfolio data into human-readable investment analysis
Example output:

“Your portfolio is heavily concentrated in the Tech sector, exposing you to sector-specific volatility. Consider diversifying across industries to reduce downside risk.”

🏗️ Architecture
Controller Layer   → Handles HTTP requests
Service Layer      → Business logic + analytics
Repository Layer   → Database access (JPA)
AI Service         → OpenAI API integration
Pipeline Service   → Market data refresh
🛠️ Tech Stack
Backend: Java, Spring Boot
Database: H2 (in-memory)
API: RESTful services
AI Integration: OpenAI API
HTTP Client: OkHttp
🔌 API Endpoints
📥 Add Asset
POST /portfolio
{
  "symbol": "AAPL",
  "quantity": 5,
  "price": 180,
  "sector": "Tech"
}
📄 Get Portfolio
GET /portfolio
📊 Allocation
GET /portfolio/allocation
🏢 Sector Allocation
GET /portfolio/sector-allocation
⚠️ Risk Analysis
GET /portfolio/risk
🔄 Refresh Market Data (Pipeline)
POST /portfolio/refresh
🤖 AI Insights
GET /portfolio/insights
⚙️ Setup Instructions
1. Clone repo
git clone <your-repo-url>
cd portfolio-service
2. Add OpenAI API Key

📁 src/main/resources/application.properties

openai.api.key=YOUR_API_KEY
3. Run the app
./mvnw spring-boot:run
4. Test API

Use Postman or curl:

curl http://localhost:8080/portfolio/insights
🧠 Design Decisions
Separation of concerns
Analytics handled in service layer
AI logic isolated in dedicated service
Hybrid intelligence approach
Deterministic calculations (allocation, risk)
AI for interpretation and insights
Extensible pipeline
Designed to integrate real market data APIs
🚀 Future Improvements
Integrate real market data APIs (e.g., Alpha Vantage)
Add caching for AI responses
Build frontend dashboard (React)
Enhance prompt engineering for better insights
Add user authentication
