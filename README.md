Algorithmic Stock Analysis Tool

This Java-based stock analysis tool automates stock screening by fetching real-time market data and analyzing technical & fundamental indicators to generate buy signals.

I would spend hours a week looking at different stocks finance sheets to find metric and see if they are a good buy,  **Instead of manually researching stocks**, this tool **automates the process** and **sends alerts when a stock meets buy criteria**, saving **hours of time** each week.

---

features
**Automated Stock Screening** – Fetches **real-time market data** using Alpha Vantage API  
**Technical Analysis** – Uses **moving averages (5-day & 10-day)** to identify buy signals  
**Fundamental Analysis** – Evaluates **P/E Ratio, P/B Ratio, ROE, EPS, and Debt-to-Equity**  
**JSON Parsing** – Extracts stock data from API responses using **Gson**  
**Automated Alerts** – Notifies when a stock is **undervalued or meets buy conditions**  
**Built with Java & Maven** – Uses **GitHub & Maven** for version control & dependency management  

---

#Tech Stack
-Programming Language**: Java
-Dependency Management**: Maven
-APIs Used: [Alpha Vantage API](https://www.alphavantage.co/)
-Version Control**: Git & GitHub

---

How It Works

1️. **fetches stock prices** from the **Alpha Vantage API**.
2️. It **analyzes historical prices** to calculate **moving averages** (technical analysis).
3️. It **retrieves financial ratios** (fundamental analysis) to determine if a stock is **undervalued**.
4️. If a stock **meets buy criteria**, it **sends an alert**

note: put your stock symbols where the symbols variable is to check stocks you want
note: there is a short term moving average version and a long term fundamental algorithm as well depending on what your intended goals are

---

**How to Run This Project**

** Clone the Repository**
```sh
git clone https://github.com/MartinHeath04/Stock-Picker.git
cd STOCKPICKERalgo
