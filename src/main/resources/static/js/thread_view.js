
function ratePost(event, action, postId) {
    event.preventDefault();

    fetch('/rate_post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            action: action,
            postId: postId
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Request failed");
        console.log("Success:", action, postId);
    })
    .catch(error => {
        console.error("Error handling post rating: ", error);
    });
}
