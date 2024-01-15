document.addEventListener('DOMContentLoaded', function () {
    // Fetch contest data and populate contest-list
    chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
      chrome.tabs.sendMessage(tabs[0].id, { action: 'fetch_contests' }, function (response) {
        displayContests(response.contests);
      });
    });
  
    // Display contests in the popup
    function displayContests(contests) {
      var contestList = document.getElementById('contest-list');
      contests.forEach(function (contest) {
        var contestItem = document.createElement('div');
        contestItem.innerHTML = `<p>${contest.name}</p><p>${contest.timing}</p><a href="${contest.link}" target="_blank">Visit Contest</a>`;
        contestList.appendChild(contestItem);
      });
    }
  });