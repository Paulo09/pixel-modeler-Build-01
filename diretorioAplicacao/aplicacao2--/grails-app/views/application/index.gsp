<!DOCTYPE html>
<html>
<head>

    <title>PixeL Framework Interoperabilidade</title>
	
	<link rel="shortcut icon" href="/assets/Key-P-32.png" type="image/x-icon" />
	
    <link href='//fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'/>
	

    <link rel="stylesheet" href="/assets/reset.css?compile=false" />

    <link rel="stylesheet" href="screen-pixel.css" />
	
	 


    <script type="text/javascript" src="/assets/swagger-lib/shred.bundle.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/jquery-1.8.0.min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/jquery.slideto.min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/jquery.wiggle.min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/jquery.ba-bbq.min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/handlebars-1.0.0.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/underscore-min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/backbone-min.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-lib/swagger.js?compile=false" ></script>
 
    <script type="text/javascript" src="/assets/swagger-ui.js?compile=false" ></script>

    <script type="text/javascript" src="/assets/swagger-lib/highlight.7.3.pack.js?compile=false" ></script>
 

    <!-- enabling this will enable oauth2 implicit scope support -->
    <script type="text/javascript" src="/assets/swagger-lib/swagger-oauth.js?compile=false" ></script>
 

    <script type="text/javascript">
        $(function () {
            window.swaggerUi = new SwaggerUi({
                url: "/api/resources",
                dom_id: "swagger-ui-container",
                supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
                onComplete: function (swaggerApi, swaggerUi) {
                    log("Loaded SwaggerUI");

                    if (typeof initOAuth == "function") {
                        /*
                         initOAuth({
                         clientId: "your-client-id",
                         realm: "your-realms",
                         appName: "your-app-name"
                         });
                         */
                    }
                    $('pre code').each(function (i, e) {
                        hljs.highlightBlock(e)
                    });
                },
                onFailure: function (data) {
                    log("Unable to Load SwaggerUI");
                },
                docExpansion: "none",
                sorter: "alpha"
            });

            $('#input_apiKey').change(function () {
                var key = $('#input_apiKey')[0].value;
                log("key: " + key);
                if (key && key.trim() != "") {
                    log("added key " + key);
                    window.authorizations.add("key", new ApiKeyAuthorization("api_key", key, "query"));
                }
            });
            window.swaggerUi.load();
        });
    </script>
</head>

<body class="swagger-section">
<div id='header'>
    <div class="swagger-ui-wrap" align="left">
	
        <a id="logo" href="/"><img src="https://img.icons8.com/ultraviolet/40/000000/powerpoint.png"/>ixeL Framework Interoperabilidade</a>
        <!--<form id='api_selector'>
            <div class='input'>
                <input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl"
                       type="text" readonly="true"/>
            </div>

            <div class='input'>
                <input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/>
            </div>

            <div class='input'><a id="explore" href="#">Explore</a></div>
        </form>-->
    </div>
</div>


<div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>

<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>