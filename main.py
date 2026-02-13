from flask import Flask, render_template, request, redirect, url_for
from datetime import datetime
import json
import os

app = Flask(__name__)

# === Настройки ===
REVIEWS_FILE = 'reviews.json'


# === Функции для работы с отзывами ===
def load_reviews():
    """Загрузка отзывов из JSON файла"""
    if os.path.exists(REVIEWS_FILE):
        with open(REVIEWS_FILE, 'r', encoding='utf-8') as f:
            try:
                return json.load(f)
            except json.JSONDecodeError:
                return []
    return []


def save_reviews(reviews):
    """Сохранение отзывов в JSON"""
    with open(REVIEWS_FILE, 'w', encoding='utf-8') as f:
        json.dump(reviews, f, ensure_ascii=False, indent=4)


# === Маршруты ===
@app.route('/')
def index():
    return render_template('index.html')


@app.route('/archive')
def history():
    return render_template('history.html')


@app.route('/photos')
def photos():
    return render_template('photos.html')


@app.route('/instrs')
def instrs():
    return render_template('instrs.html')


@app.route('/wallpapers')
def wallpapers():
    return render_template('wallpapers.html')


@app.route('/download')
def download():
    return render_template('download.html')


@app.route('/otzivy', methods=['GET', 'POST'])
def otzivy():
    reviews = load_reviews()

    if request.method == 'POST':
        name = request.form.get('name', 'Аноним')
        rating = int(request.form.get('rating', 5))
        comment = request.form.get('comment', '').strip()

        if comment:
            new_review = {
                'name': name,
                'rating': rating,
                'comment': comment,
                'date': datetime.now().strftime('%d.%m.%Y %H:%M')
            }
            reviews.append(new_review)
            save_reviews(reviews)

        return redirect(url_for('otzivy'))

    return render_template('otzivy.html', reviews=reviews)


@app.route('/404')
def error():
    return render_template('error.html')


@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html', error=error), 404


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=False)
