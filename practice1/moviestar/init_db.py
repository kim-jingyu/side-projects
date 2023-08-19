import random
import requests
from bs4 import BeautifulSoup

from pymongo import MongoClient

client = MongoClient('mongodb://test:test@localhost', 27017)
db = client.dbjungle


def insert_all():
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
    request = requests.get('https://movie.daum.net/ranking/boxoffice/yearly', headers=headers)
    soup = BeautifulSoup(request.text, 'html.parser')

    movies = soup.select('#mainContent > div > div.box_boxoffice > ol > li')

    for movie in movies:
        title_tag_element = movie.select_one('a[class="link_txt"]')
        if not title_tag_element:
            continue
        title = title_tag_element.text

        # 존재하지 않는 영화인 경우에만 추가한다.
        found_movies = list(db.movies.find({'title': title}))
        if found_movies:
            continue

        open_date_tag_element = movie.select_one('.txt_info > .info_txt:nth-child(1) > .txt_num')
        if not open_date_tag_element:
            continue
        open_date = open_date_tag_element.text
        year, month, day = map(int, open_date.split('.'))
        year += 2000

        audience_tag_element = movie.select_one('.txt_info > .info_txt:nth-child(2)')
        if not audience_tag_element:
            continue
        audience = audience_tag_element.findChild(string=True, recursive=False)
        audience = int(''.join([c for c in audience if c.isdigit()]))

        poster_url_tag_element = movie.select_one('.poster_movie > img')
        if not poster_url_tag_element:
            continue
        poster_url = poster_url_tag_element['src']

        poster_info_tag_element = movie.select_one('.poster_info > a')
        if not poster_info_tag_element:
            continue
        poster_info = poster_info_tag_element['href']
        if not poster_info:
            continue
        poster_info = "https://movie.daum.net" + poster_info

        likes = random.randrange(0, 100)

        doc = {
            'title': title,
            'open_year': year,
            'open_month': month,
            'open_day': day,
            'audience': audience,
            'poster_url': poster_url,
            'poster_info': poster_info,
            'likes': likes,
            'trashed': False
        }

        db.movies.insert_one(doc)
        print('완료:', title, year, month, day, audience, poster_url, poster_info, likes)


if __name__ == '__main__':
    db.movies.drop()

    insert_all()
