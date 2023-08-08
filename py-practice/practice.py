people = [{'name': 'bob', 'age': 20},
          {'name': 'carry', 'age': 38},
          {'name': 'john', 'age': 7},
          {'name': 'smith', 'age': 17},
          {'name': 'ben', 'age': 27}]


def get_age(name):
    for person in people:
        if person.get('name') == name:
            return person.get('age')
    return '해당 이름이 없습니다.'


print(get_age('bob'))
