
export const getMessage = async () => {
  try {
    const response = await fetch('/user/getMessage', {
      method: 'GET',
      header: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }});
    return await response.json();
  }
  catch (e) {
    console.log(e);
  }
}