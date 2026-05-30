const input = document.getElementById("cityInput");
const hidden = document.getElementById("cityHidden");
const box = document.getElementById("suggestions");

let timeout;

input.addEventListener("input", () => {
    clearTimeout(timeout);

    timeout = setTimeout(async () => {
        const q = input.value;

        if (q.length < 2) {
            box.innerHTML = "";
            return;
        }

        const res = await fetch(
            `/profile/edit/city/search?query=` + encodeURIComponent(q)
        );

        const data = await res.json();

        box.innerHTML = "";

        data.forEach(city => {
            const div = document.createElement("div");
            div.textContent = city;

            div.onclick = () => {
                input.value = city;
                hidden.value = city;
                box.innerHTML = "";
            };

            box.appendChild(div);
        });
    }, 300);
});