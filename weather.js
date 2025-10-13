async function getWeather() {
    const apiKey = 'aqxyrwb1ojrd4ktaykymlufmy2x1juvhw4zdrx4k'
    const city = 'Karaganda';
    const url = `https://api.weatherapi.com/v1/current.json?key=${apiKey}&q=${city}&lang=ru`;

    try {
        const response = await fetch(url);
        const data = await response.json();
        displayWeather(data);
    } catch (error) {
        console.error('Ошибка при получении данных:', error);
    }
}

function displayWeather(data) {
    const weatherElement = document.getElementById('weather');
    if (data && data.current) {
        weatherElement.innerHTML = `
            <p>Город: ${data.location.name}</p>
            <p>Температура: ${data.current.temp_c}°C</p>
            <p>Состояние: ${data.current.condition.text}</p>
            <img src="https:${data.current.condition.icon}" alt="Погода">
        `;
    }
}

window.onload = getWeather;