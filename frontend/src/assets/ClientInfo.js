// get browser
function GetCurrentBrowser() {
  let ua = navigator.userAgent.toLocaleLowerCase();
  let browserType = null;
  if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
    browserType = "IE";
  } else if (ua.match(/firefox/) != null) {
    browserType = "firefox";
  } else if (ua.match(/chrome/) != null) {
    browserType = "chrome";
  } else if (ua.match(/safari/) != null) {
    browserType = "Safari";
  } else {
    browserType = "others";
  }
  return browserType;
}

// get ip address
function GetIpAddress() {
  return localStorage.getItem("Ip");
}

export { GetIpAddress, GetCurrentBrowser };
