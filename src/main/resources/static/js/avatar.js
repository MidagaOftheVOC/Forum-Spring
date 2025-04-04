document.addEventListener("DOMContentLoaded", () => {
    const uploadForm = document.getElementById("uploadForm");
    const fileInput = document.getElementById("fileInput");



    uploadForm.addEventListener("submit", async (event) => {

        event.preventDefault(); // do not refresh page on submission

        const file = fileInput.files[0];

        if(!file){
            alert("Please select a file of the appropriate type");
            return;
        }

        // file component for the POST
        const formData = new FormData();
        formData.append("avatar", file);

        fetch("/upload_avatar", {
            method: "POST",
            body: formData
        })
        .then(response => {
            if (response.ok) {
                alert("Upload successful!");
            } else {
                alert("Upload failed. [" + response.status + "]");
            }
        })
        .catch(err => {
            console.error("Error in avatar upload process");
        });
    }
});