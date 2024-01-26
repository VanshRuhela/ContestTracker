// Sample content.js, adjust according to the coding contest websites
var contests = [
    { name: 'CF Sample round', timing: 'Jan/15/2024 20:05UTC+5.5', link: 'https://codeforces.com/contests/1921' },
  ];
  
  chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
    if (request.action === 'fetch_contests') {
      sendResponse({ contests: contests });
    }
  });

