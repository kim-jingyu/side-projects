import requests
import re
from bs4 import BeautifulSoup

headers = {"User-Agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}

for i in range(1, 6):
    print("현재 페이지:", i)

    url = f"https://www.coupang.com/np/search?q=%EC%95%8C%EB%A1%9C%EC%97%90+%EC%A0%A4%EB%A6%AC&channel=auto&component=&eventCategory=SRP&trcid=&traid=&sorter=scoreDesc&minPrice=&maxPrice=&priceRange=&filterType=&listSize=36&filter=&isPriceRange=false&brand=&offerCondition=&rating=0&page={i}&rocketAll=false&searchIndexingToken=1=9&backgroundColor="

    res = requests.get(url, headers)
    res.raise_for_status()

    soup = BeautifulSoup(res.text, "lxml")

    items = soup.find_all("li", attrs={"class":re.compile("^search-product")})
    # print(items[1].find("div", attrs={"class":"name"}).get_text())
    for item in items:

        # 광고 상품은 제외
        ad_badge = item.find("span", attrs={"class": "ad-badge-text"})
        if ad_badge:
            continue

        name = item.find("div", attrs={"class": "name"}).get_text()
        price = item.find("strong", attrs={"class": "price-value"}).get_text()

        # 리뷰 10개 이상, 평점 4.5 이상 되는 것만 조회
        rating = item.find("em", attrs={"class": "rating"})
        if rating:
            rating = rating.get_text()
        else:
            rating = "평점 없음"
            continue

        rating_count = item.find("span", attrs={"class": "rating-total-count"})
        if rating:
            rating_count = rating_count.get_text()[1:-1]
        else:
            rating_count = "평점 수 없음"
            continue

        link = item.find("a", attrs={"class": "search-product-link"})['href']

        if float(rating) >= 4.5 and int(rating_count) >= 10:
            print(name, price, rating, rating_count)
            print(f"제품명 : {name}")
            print(f"가격 : {price}")
            print(f"평점 : {rating}점 ({rating_count} 개)")
            print("바로가기 : {}".format("https://www.coupang.com" + link))
            print("-" * 100) # 줄긋기