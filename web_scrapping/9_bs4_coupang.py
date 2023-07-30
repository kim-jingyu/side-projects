import requests
import re
from bs4 import BeautifulSoup

url = "https://www.coupang.com/np/search?q=%EA%B0%A4%EB%9F%AD%EC%8B%9C%ED%83%AD&channel=relate&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page=1&rocketAll=false&searchIndexingToken=1=9&backgroundColor="
headers = {"User-Agent":"IE11 : Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; rv:11.0) like Gecko"}

res = requests.get(url, headers)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")

items = soup.find_all("li", attrs={"class":re.compile("^search-product")})
# print(items[1].find("div", attrs={"class":"name"}).get_text())
for item in items:

    # 광고 상품은 제외
    ad_badge = item.find("span", attrs={"class": "ad-badge-text"})
    if ad_badge:
        print("--광고 상품은 제외--")
        continue

    name = item.find("div", attrs={"class": "name"}).get_text()
    price = item.find("strong", attrs={"class": "price-value"}).get_text()

    # 리뷰 10개 이상, 평점 4.5 이상 되는 것만 조회
    rating = item.find("em", attrs={"class": "rating"})
    if rating:
        rating = rating.get_text()
    else:
        rating = "평점 없음"
        print("--평점 없는 상품 제외--")
        continue

    rating_count = item.find("span", attrs={"class": "rating-total-count"})
    if rating:
        rating_count = rating_count.get_text()
        rating_count = rating_count[1:-1]
        print("리뷰 수 :",rating_count)
    else:
        rating_count = "평점 수 없음"
        print("--평점 수 없는 상품 제외--")
        continue

    if float(rating) >= 4.5 and int(rating_count) >= 10:
        print(name, price, rating, rating_count)