<template>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <link href='https://fonts.googleapis.com/css?family=Oswald:400,700' rel='stylesheet' type='text/css'>

    <div id='friendRequest'>
        <div dir='ltr' style='text-align: left;' trbidi='on'>
            <div class='separator' style='clear: both; text-align: justify;'>
            </div>
            <div style='text-align: justify;'>
                <a href='#' imageanchor='1' style='clear: left; float: left; margin-bottom: 1em; margin-right: 1em;'><img
                        border='2' height='130' src='https://tinypic.host/images/2023/02/21/pikrepo.com-2.jpg'
                        width='120' /></a>
            </div>
            <div style='text-align: justify; padding: 10px 10px;'>
                This text is an example of text that can be replaced in the same space. This text was generated from the
                <br>
                <div class="wrapper">
                    <div class="icon accept">
                        <div class="tooltip">Accept</div>
                        <span><i class="fa fa-check"></i></span>
                    </div>
                    <div class="icon refuse">
                        <div class="tooltip">Refuse</div>
                        <span><i class="fa fa-times"></i></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

</script>

<style scoped>
body {
    display: flex;
    flex-direction: column;
    justify-content: center;
    background: #f2f2f2 url('https://tinypic.host/images/2023/04/01/U6v9jjz.jpg');
    align-items: center;
    height: 100vh;

}

#friendRequest {
    text-align: left;
    color: #fff;
    padding: 5px;
    background: #222;
    font-weight: bold;
    font: 16px 'Oswald';
    height: 140px;
    width: 100%;
    max-width: 455px;
    float: left;
    margin-top: 0px;
    border: 4px solid #C8102E;
    border-radius: 8px;
    -webkit-background-clip: padding-box;
    -webkit-box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
    -moz-box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
    border-radius: 8px 8px 8px 8px;
    -webkit-transition: all 0.3s ease-in-out;
    -moz-transition: all 0.3s ease-in-out;
    position: absolute;
    box-shadow: 8px 8px 8px rgba(0, 0, 0, 0.6);
}

#friendRequest:hover {
    background: #2980b9;
    border: 4px solid #3498db;
    box-shadow: 8px 8px 8px rgba(0, 0, 0, 0.6);
}

#friendRequest a.fb {
    background: #34495e;
    color: #fff;
    -webkit-transition: all 0.3s ease-in-out;
    -moz-transition: all 0.3s ease-in-out;
}

#friendRequest a.twit {
    background: #4099FF;
    color: #fff;
    -webkit-transition: all 0.3s ease-in-out;
    -moz-transition: all 0.3s ease-in-out;
}

#friendRequest a.penn {
    background: #7f8c8d;
    color: #fff;
    -webkit-transition: all 0.3s ease-in-out;
    -moz-transition: all 0.3s ease-in-out;
}

#friendRequest a {
    color: white;
    text-decoration: none;
    outline: none;
}

.wrapper {
    display: inline-flex;
}

.wrapper .icon {
    position: relative;
    background: #C8102E;
    padding: 10px;
    margin: 2px;
    top: 22px;
    width: 5px;
    height: 5px;
    font-size: 14px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
    position: absolute;
    top: 0;
    font-size: 14px;
    background: #fff;
    color: #ffffff;
    padding: 5px 8px;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    opacity: 0;
    pointer-events: none;
    border-radius: 4px;
    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    -ms-border-radius: 4px;
    -o-border-radius: 4px;
}

.wrapper .tooltip::before {
    position: absolute;
    content: "";
    height: 8px;
    width: 8px;
    background: #fff;
    bottom: -3px;
    left: 50%;
    transform: translate(-50%) rotate(45deg);
    transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -webkit-transform: translate(-50%) rotate(45deg);
    -moz-transform: translate(-50%) rotate(45deg);
    -ms-transform: translate(-50%) rotate(45deg);
    -o-transform: translate(-50%) rotate(45deg);
    -webkit-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -moz-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -ms-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    -o-transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .icon:hover .tooltip {
    top: -45px;
    opacity: 1;
    visibility: visible;
    pointer-events: auto;
}

.wrapper .icon:hover span,
.wrapper .icon:hover .tooltip {
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.01);
}

.wrapper .accept:hover,
.wrapper .accept:hover .tooltip,
.wrapper .accept:hover .tooltip::before {
    background: #3b5999;
    color: #fff;
}

.wrapper .refuse:hover,
.wrapper .refuse:hover .tooltip,
.wrapper .refuse:hover .tooltip::before {
    background: #46c1f6;
    color: #fff;
}</style>