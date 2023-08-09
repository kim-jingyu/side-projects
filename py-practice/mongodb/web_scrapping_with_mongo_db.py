import requests
from bs4 import BeautifulSoup
from pymongo import MongoClient


def insert_all():
    # URL을 읽어서 HTML을 받아온다.
    headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}
    data = requests.get('https://movie.daum.net/ranking/boxoffice/yearly', headers=headers)

    # HTML 을 BeautifulSoup 라이브러리를 활용해 검색에 용이한 상태로 만든다.
    soup = BeautifulSoup(data.text, 'html.parser')

    # select를 이용해서 li 태그들을 불러와서 movies 변수에 저장한다.
    movies = soup.select('#mainContent > div > div.box_boxoffice > ol > li')
    print(len(movies))

    # li (영화들) 를 반복하여,
    for movie in movies:
        # 영화 제목을 추출하여 title 변수에 저장한다.
        title_tag_element = movie.select_one('.tit_item > a')
        if not title_tag_element:
            continue
        title = title_tag_element.text

        # 개봉일을 추출하여 open_date 변수에 저장한다.
        open_date_tag_element = movie.select_one('div > div.thumb_cont > span > span:nth-child(1) > .txt_num')
        if not open_date_tag_element:
            continue
        open_date = open_date_tag_element.text

        # 년도.월.일 형태에 년도, 월, 일을 추출한다.
        open_year, open_month, open_day = map(int, open_date.split('.'))

        # 개봉년도를 22년 형태에서 2022년 형태로 바꾸기 위해서 2000을 더해준다.
        open_year += 2000

        # 누적 관객수를 추출하여 viewers 변수에 저장한다.
        viewers_tag_element = movie.select_one('div > div.thumb_cont > span > span:nth-child(2)')
        if not viewers_tag_element:
            continue
        viewers_tag_element.find('span').decompose()
        viewers = viewers_tag_element.text

        print("완료:", title, open_year, open_month, open_day, viewers)

if __name__ == '__main__':
    # 영화 사이트를 스크래핑해서 db에 채우기
    insert_all()
