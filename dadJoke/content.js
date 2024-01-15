// Sample content.js, adjust according to the coding contest websites
var contests = [
    { name: 'Contest 1', timing: '2024-01-01 12:00 PM', link: 'https://contest1.com' },
    { name: 'Contest 2', timing: '2024-01-15 3:00 PM', link: 'https://contest2.com' },
  ];
  
  chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
    if (request.action === 'fetch_contests') {
      sendResponse({ contests: contests });
    }
  });
  