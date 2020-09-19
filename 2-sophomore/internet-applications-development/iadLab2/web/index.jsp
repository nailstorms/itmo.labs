<%@ page import="java.util.ArrayList" %>
<%@ page import="lab2.Dot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <title>Lab 2</title>
  <meta charset="utf-8">
  <link rel="icon" href="images/favicon.ico">
  <link rel="stylesheet" type="text/css" href="styles.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src="validation.js"></script>
</head>

<body>
<header class="box boxHeader">
  <b>Kokov A.T.</b> & <b>Nesterov D.K.</b><br>
  Group No. P3202<br>
  Variant No. 96418
</header>

<div class="flexContainer" style="margin-top: -40px">
  <svg class="graph" ref="svg" xmlns="http://www.w3.org/2000/svg" width = "400" height="400">
    <g id="graph__coordinate-plane">

      <path fill="none" stroke="#000" stroke-width="1px" d="M 0 200 h 400"></path>
      <path fill="none" stroke="#000" stroke-width="1px" d="M 200 0 v 400"></path>

      <path fill="none" stroke="#000" stroke-width="1px" d="M 200 0 l -3 7"></path>
      <path fill="none" stroke="#000" stroke-width="1px" d="M 200 0 l 3 7"></path>
      <path fill="none" stroke="#000" stroke-width="1px" d="M 400 200 l -6 -3"></path>
      <path fill="none" stroke="#000" stroke-width="1px" d="M 400 200 l -6 3"></path>
    </g>
    <path fill="#3399ff" stroke="#000" stroke-width="1px" d="M 200 40 v 160 h -80 Z"></path>
    <path fill="#3399ff" stroke="#000" stroke-width="1px" d="M 40 200 q 0 160 160 160 v -160 Z"></path>
    <path fill="#3399ff" stroke="#000" stroke-width="1px" d="M 200 200 h 160 v 160 h -160 Z"></path>

    <text x="180" y="44" font-weight="400"><tspan x="180" y="44" font-size="16px">R</tspan></text>
    <text x="106" y="190" font-weight="400"><tspan x="20" y="190" font-size="16px">R</tspan></text>
    <text x="166" y="296" font-weight="400"><tspan x="209" y="380" font-size="16px">R</tspan></text>
    <text x="360" y="190" font-weight="400"><tspan x="360" y="190" font-size="16px">R</tspan></text>
    <text x="360" y="190" font-weight="400"><tspan x="90" y="190" font-size="16px">R/2</tspan></text>
    <g id="graph__points">

    </g>
  </svg>

  <div class="box" style="margin-bottom: 8px">
    <form action="../../calculation.php" method="get" id="form">
      R: &emsp;<input type="text" name="r" id="r" placeholder="[2 ... 5]"><br>
      <table style="display: inline-table">
        <tr>
          <td>X: &ensp;</td>
          <td><label><input type="checkbox" name="x" value="1">1 &nbsp;</label></td>
          <td><label><input type="checkbox" name="x" value="2">2 &nbsp;</label></td>
          <td><label><input type="checkbox" name="x" value="3">3 &nbsp;</label></td>
        </tr>
        <tr>
          <td></td>
          <td><label><input type="checkbox" name="x" value="0">0</label></td>
          <td><label><input type="checkbox" name="x" value="-1">-1</label></td>
          <td><label><input type="checkbox" name="x" value="-2">-2</label></td>
        </tr>
        <tr>
          <td></td>
          <td><label><input type="checkbox" name="x" value="-3">-3</label></td>
          <td><label><input type="checkbox" name="x" value="-4">-4</label></td>
          <td><label><input type="checkbox" name="x" value="-5">-5</label></td>
        </tr>
      </table>
      <br>
      Y: &emsp;<input type="text" name="y" id="y" placeholder="[-5 ... 3]">
      <br/> <input type="submit" id = "check" value="Check for belonging" onClick= "//checkValid(event)">
      <br/>
      <br/> <input type="button" id = "cle" value="Clear current R checks" onClick= "cl()">
      <br/>
      <br/> <input type="button" id = "clAll" value="Clear all R checks" onClick= "clearAll()">
      <br/>
      <%-- <button type="submit" id="check">Submit</button> --%>
    </form>
  </div>
  <div class="box boxError hidden" id="error">
    Y is not a number!
  </div>
  <div class="box boxResult hidden" id="boxResult">
    Результат: <span class="bold" id="resultValue"></span>
  </div>
  <table style="margin-top: 8px" class="resultTable">
    <thead>
    <tr>
      <td class="tdWithBorders">R</td>
      <td class="tdWithBorders">X</td>
      <td class="tdWithBorders">Y</td>
      <td class="tdWithBorders">Result</td>
    </tr>
    </thead>
    <tbody id="resultTable">

    </tbody>
    <script>

        function cl() {
            areaRads[rad-1].length=0; //-1
            drawArea();
            let tbl = document.getElementById("resultTable");
            let rowC = tbl.rows.length;
            for (let i = rowC - 1; i > 0; i--) {
                if (tbl.rows[i].cells[2].textContent.trim() === rad) {
                    tbl.deleteRow(i);
                }
            }
            fetch(`clearHistory?rad=${rad}`, { method: 'GET' });

        }
        function clearAll() {
            for (r=0; r<areaRads.length; r++) {
                areaRads[r].length=0;
            }
            drawArea();
            let tbl = document.getElementById("resultTable");
            let rowCount = tbl.rows.length;
            for (let i = rowCount-1; i > 0; i--) {
                tbl.deleteRow(i);
            }
            fetch(`clearHistory`, { method: 'GET'});
        }

        /*
        function sendRequestWithCoordinates(x, y, radius) {
            points.push([x, y]);
            fetch(`areaCheck/?x=${x}&y=${y}&rad=${rad}`, {
                method: 'GET',
            }).then(response => response.json())
                .then(data => {
                    var jsonArrStr = '[' + data.toString() + ']';
                    var arr = JSON.parse(jsonArrStr);
                    document.getElementById("results").innerHTML = "<tr>" +
                        "<th>N</th>" +
                        "<th>Y</th>" +
                        "<th>X</th>" +
                        "<th>R</th>" +
                        "<th>&isin;</th></tr>";
                    var counter = 1;
                    arr.forEach(function (elem) {
                        document.getElementById("results").innerHTML +=
                            "<tr class='res_elem'><td>" + counter +
                            "</td><td>" + elem["x"] +
                            "</td><td>" + elem["y"] +
                            "</td><td>" + elem["radius"] +
                            "</td><td>" + elem["isIn"] +
                            "</td></tr>";
                        counter++;
                    });
                    $("#results_field").removeClass("hidden");
                    redraw(document.getElementById('r'));
                }).catch(err => {
                console.log(err)
            });
        }

        var pt = document.getElementById('graph').createSVGPoint();
        graph.onclick = function(event) {
            pt.x = event.clientX;
            pt.y = event.clientY;
            var cursorpt =  pt.matrixTransform(document.getElementById('graph').getScreenCTM().inverse());
            if ((document.getElementById('r').value)>=2) {
                //points.push([cursorpt.x*document.getElementById('r').value/150, cursorpt.y]);
                sendRequestWithCoordinates((cursorpt.x-170)*document.getElementById('r').value/150, -(cursorpt.y-170)*document.getElementById('r').value/150, document.getElementById('r').value);
            }
            redraw(document.getElementById('r'));
        };

        var x = null;
        $(".container .container table button").on("click", function (e) {
            e.preventDefault();
            if (x === parseInt(e.currentTarget.innerText)) {
                x = null;
                document.querySelector("#x").value = x;
                $("table button").removeClass("active");
                return;
            }
            x = parseInt(e.currentTarget.innerText);
            document.querySelector("#x").value = x;
            $("table button").removeClass("active");
            $(e.currentTarget).addClass("active");
        });

        $("form#form").on("submit", function (e) {
            e.preventDefault();
            var fd = new FormData(e.currentTarget);
            var x = parseInt(fd.get("x"));
            var y = fd.get("y");
            y = y.replace(",", ".");
            var r = fd.get("r");
            r = r.replace(",", ".");
            var endOfLight = false;
            $("label").removeClass("invalid");
            if (!(x <= 3 && x >= -5)) {
                $("#inputs table button").addClass("invalid");
                endOfLight = true;
            }
            try {
                if (y != parseFloat(y)) throw true;
                y = parseFloat(y);
                if (y > 3 || y < -3) {
                    throw true;
                }
            } catch (e) {
                $("#y").addClass("invalid");
                endOfLight = true;
            }
            try {

                if (r != parseFloat(r)) throw true;
                r = parseFloat(r);
                if (r > 5 || r < 2) {
                    throw true;
                }
            } catch (e) {
                $("#r").addClass("invalid");
                endOfLight = true;
            }
            if (endOfLight) return;
            const params = new URLSearchParams();
            for (const pair of fd.entries()) {
                params.append(pair[0], pair[1]);
            }
            sendRequestWithCoordinates(params.get("x"), params.get("y"), params.get("r"));
        });

        $("input[type = text]").on("click", function (event) {
            $(event.currentTarget).removeClass("invalid");
        });
        $("#inputs table button").on("click", function (event) {
            $("#inputs table button").removeClass("invalid");
        });
        */
    </script>
  </table>
</div>
</body>
</html>