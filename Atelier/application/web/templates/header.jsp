<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>atelier</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="assets/img/favicon.png" rel="icon">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="assets/fonts/fontawesome-all.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="assets/css/template.css" rel="stylesheet">
    <link href="assets/css/table.css" rel="stylesheet">
    <link href="assets/css/formulaire.css" rel="stylesheet">

    <!-- =======================================================
    * Template Name: NiceAdmin - v2.5.0
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>
<body>

    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top d-flex align-items-center">

        <div class="d-flex align-items-center justify-content-between">
            <a href="index.html" class="logo d-flex align-items-center">
                <img src="assets/img/logo.png" alt="">
                <span class="d-none d-lg-block"></span>
            </a>
            <i class="bi bi-list toggle-sidebar-btn"></i>
        </div><!-- End Logo -->

    </header><!-- End Header -->

    <!-- ======= Sidebar ======= -->
    <aside id="sidebar" class="sidebar">

        <ul class="sidebar-nav" id="sidebar-nav">

            <li class="nav-heading">Atelier reparation</li>

            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#client-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Client</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="client-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementClientServlet">
                            <i class="bi bi-circle"></i><span>Clients</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementClientServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#employe-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Employe</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="employe-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementEmployeServlet">
                            <i class="bi bi-circle"></i><span>Employes</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementEmployeServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#appareil-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Appareil</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="appareil-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementAppareilServlet">
                            <i class="bi bi-circle"></i><span>Appareil</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementAppareilServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#reparation-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Reparation</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="reparation-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementReparationServlet">
                            <i class="bi bi-circle"></i><span>Liste reparation</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementAppareil_employeServlet?action=form">
                            <i class="bi bi-circle"></i><span>Attribution appareil</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementReparationServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#piece-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Piece Detache</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="piece-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementEntree_pieceServlet?action=form">
                            <i class="bi bi-circle"></i><span>commande</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementPieceServlet">
                            <i class="bi bi-circle"></i><span>pieces</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementPieceServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#typa-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Typa</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="typa-nav" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="TraitementTypaServlet">
                            <i class="bi bi-circle"></i><span>Typas</span>
                        </a>
                    </li>
                    <li>
                        <a href="TraitementTypaServlet?action=form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                </ul>
            </li>            

        </ul>

    </aside><!-- End Sidebar -->

</body>
</html>
