function submit(event){
    event.preventDefault();
    var y = document.getElementById("y").value
    console.log(y);

    var error = document.getElementById("error");
    var boxResult = document.getElementById("boxResult");
    var resultValue = document.getElementById("resultValue");
    if (y === ""){
        error.classList.remove("hidden");
        error.textContent = "Значение Y — не число!";
    }
    else if (r === ""){
        error.classList.remove("hidden");
        error.textContent = "Значение R — не число!";
    }
    else {
        y = Number(y);
        if (isNaN(y)) {
            boxResult.classList.add("hidden");
            error.classList.remove("hidden");
            error.textContent = "Значение Y — не число!"
        }
        else if (y <= -5 || y >= 3){
            boxResult.classList.add("hidden");
            error.classList.remove("hidden");
            error.textContent = "Y must be in range [-5 ... 3]"
        }
        else {
            function something(formData) {
                let params = new URLSearchParams();
                for (let kv of formData) { params.append(kv[0], kv[1]); }
                return params.toString();
            }
            var formData = new FormData(document.getElementById("form"));
            var url = "calculation.php?" + something(formData);
            fetch(url, { method: "GET"})
                .then(function(response) { return response.text(); })
                .then(function(html) {
                    document.getElementById("resultTable").insertAdjacentHTML("beforeend", html);
                    error.classList.add("hidden");
                    boxResult.classList.remove("hidden");
                    resultValue.textContent = document.querySelector("#resultTable tr:last-of-type td:nth-of-type(4)").textContent;
                });
        }
    }

}




/*
function placePoint(e) {
    const referencePt = e.target.closest('svg');
    referencePt.x = e.clientX;
    referencePt.y = e.clientY;
    const axisDim = 400;
    const rDim = 160;
    const { x: graphX, y: graphY } = referencePt.matrixTransform(
        e.target.closest('svg').getScreenCTM().inverse());
    const x = (graphX - (axisDim / 2)) / rDim;
    const y = -((graphY - (axisDim / 2)) / rDim);
    this.$emit('point-placed', { x, y });

}
*/

function buttonAction(event){
    event.preventDefault();
    document.querySelector("#x").value = event.currentTarget.innerText;
    Array.prototype.slice.call(document.querySelectorAll("button")).forEach(function(el){el.classList.remove("active")});
    event.currentTarget.classList.add("active");
}

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("check").addEventListener("click", submit);
    Array.prototype.slice.call(document.querySelectorAll("button")).forEach(function (e) { if(e.id !== "clearButton")e.addEventListener("click", buttonAction)});
});