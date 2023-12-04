import requests
from bs4 import BeautifulSoup

from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.jungle


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

        img_src_tag_element = movie.select_one('.poster_movie > img')
        if not img_src_tag_element:
            continue
        img_src = img_src_tag_element['src']

        doc = {
            'title': title,
            'open_year': year,
            'open_month': month,
            'open_day': day,
            'audience': audience,
            'img_src': img_src
        }

        db.movies.insert_one(doc)
        print('완료:', title, year, month, day, audience, img_src)


if __name__ == '__main__':
    db.movies.drop()

    insert_all()
