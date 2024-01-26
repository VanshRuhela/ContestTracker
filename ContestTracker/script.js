var contests = [
    { name: 'CF Sample round', timing: 'Jan/15/2024 20:05UTC+5.5', link: 'https://codeforces.com/contests/1921' },
  ];

  jokeElement.innerHTML = contests[0].name;


// fetch('https://icanhazdadjoke.com/slack')
//     .then(data => data.json())
//     .then(jokeData => {
//         const jokeText = jokeData.attachments[0].text;
//         const jokeElement = document.getElementById('jokeElement');

//         jokeElement.innerHTML = jokeText + contests[0].name;
        
//     })