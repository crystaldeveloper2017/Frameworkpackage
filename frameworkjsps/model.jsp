<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="jspName" value='${requestScope["outputObject"].get("contentJspName")}' />
<c:set var="userName" value='${requestScope["outputObject"].get("userName")}' />
<c:set var="elementsDB" value='${requestScope["outputObject"].get("elementsDB")}' />

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${projectName}</title>
  <link rel="icon" href="https://img.icons8.com/emoji/48/000000/cloud-emoji.png" type="image/png" sizes="16x16">

  <!-- CRITICAL: this prevents the page from zooming out on mobile -->
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

  <!-- CSS & LIBRARIES -->
  <link rel="stylesheet" href="css/jquery-ui.css">
  <link rel="stylesheet" href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
  <link rel="stylesheet" href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="css/font-awesome.min.css">
  <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  <link rel="stylesheet" href="css/site.css">
  <link rel="stylesheet" href="css/richtext.min.css">
  <link rel="stylesheet" href="plugins/toastr/toastr.min.css">

  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

  <!-- SCRIPTS -->
  <script>
    var IS_ADMIN = <%= (session.getAttribute("adminFlag") != null && (Boolean) session.getAttribute("adminFlag")) %>;
  </script>
  <script src="js/common.js"></script>
  <script src="plugins/jquery/jquery.min.js"></script>
  <script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
  <script src="dist/js/adminlte.min.js"></script>
  <script src="dist/js/demo.js"></script>
  <script src="js/jquery-ui.js"></script>
  <script src="plugins/toastr/toastr.min.js"></script>
  <script src="plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
  <script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
  <script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600;700&family=Inter:wght@400;500;600&display=swap');

  /* ============================================================
     ROOT VARIABLES (Auric theme + touch target base)
  ============================================================ */
  :root {
    --gold: #C9A227;
    --gold-bright: #E8C547;
    --gold-dim: #8a7220;

    /* Light mode */
    --bg: #FAF7EF;
    --surface: #FFFFFF;
    --text: #241F14;
    --text-muted: #6b6250;
    --border: #E5DCC3;
    --shadow: rgba(36, 31, 20, 0.08);

    --primary-color: #d4af37;
    --primary-soft: #e8c86e;
    --primary-dark: #b89122;
    --secondary-color: #6c757d;
    --background-color: #FAF7EF;
    --text-color: #241F14;
    --navbar-bg: #ffffff;
    --touch-min: 44px;
  }

  [data-theme="dark"] {
    --bg: #14120C;
    --surface: #1E1B12;
    --text: #F3ECD9;
    --text-muted: #A99C7A;
    --border: #33301F;
    --shadow: rgba(0, 0, 0, 0.4);

    --background-color: #14120C;
    --text-color: #F3ECD9;
    --navbar-bg: #1E1B12;
  }

  /* ============================================================
     GLOBAL RESET — prevent horizontal overflow
  ============================================================ */
  *, *::before, *::after { box-sizing: border-box; }

  html { max-width: 100%; }
  body {
    max-width: 100%;
    overflow-x: clip;
  }

  body, .content-wrapper, .wrapper {
    background: var(--bg) !important;
    color: var(--text) !important;
    transition: background 0.35s ease, color 0.35s ease;
    font-family: 'Inter', sans-serif;
  }

  .content-wrapper {
    margin-left: 0 !important;
    width: 100% !important;
    min-height: 100vh;
  }

  /* ============================================================
     NAVBAR
  ============================================================ */
  .main-header.navbar {
    background-color: var(--surface) !important;
    border-bottom: 1px solid var(--border) !important;
    box-shadow: 0 2px 12px var(--shadow) !important;
    transition: background 0.35s ease, border-color 0.35s ease !important;
    padding: 0 10px;
    min-height: 50px;
    width: 100%;
    flex-wrap: nowrap;
    overflow: visible; /* must NOT be hidden — clips search/menu dropdowns */
  }

  .main-header .nav-link, .main-header .nav-link strong, .main-header .dropdown-toggle strong {
    color: var(--text) !important;
    font-weight: 600;
    white-space: nowrap;
    transition: color 0.25s ease;
  }
  .main-header .nav-link:hover { color: var(--gold-bright) !important; }

  #divTitle {
    color: var(--gold);
    font-weight: 700;
    font-size: 1.2rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  /* ============================================================
     TABLE / BUTTONS
  ============================================================ */
  table thead th, table th {
    background-color: var(--gold) !important;
    color: #fff !important;
    text-align: center;
    font-weight: 600;
  }
  td { color: var(--text); }
  .btn-primary { background-color: var(--gold); border-color: var(--gold); color: #fff; }
  .btn-primary:hover { background-color: var(--gold-bright); border-color: var(--gold-bright); }
  .modal-content {
    border-radius: 10px;
    border: 2px solid var(--gold);
    background-color: var(--surface);
    color: var(--text);
  }

  /* ============================================================
     SHARED ROUND ICON BUTTON (search / menu-browser triggers)
  ============================================================ */
  .nav-icon-btn {
    background: none;
    border: 1.5px solid var(--gold);
    border-radius: 50%;
    width: var(--touch-min);
    height: var(--touch-min);
    min-width: var(--touch-min);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: var(--gold-dim);
    font-size: 1rem;
    transition: background 0.2s, color 0.2s;
    padding: 0;
    flex-shrink: 0;
    -webkit-tap-highlight-color: transparent;
    touch-action: manipulation;
  }
  .nav-icon-btn:hover, .nav-icon-btn:active {
    background: var(--gold);
    color: #fff;
  }

  /* ============================================================
     DESKTOP SEARCH (hidden on mobile)
  ============================================================ */
  #navSearchWrapper {
    position: relative;
    display: flex !important;
    align-items: center;
    gap: 6px;
  }

  #navSearchInput {
    width: 200px;
    border: 1px solid var(--border) !important;
    background: var(--bg) !important;
    color: var(--text) !important;
    border-radius: 20px;
    padding: 6px 14px;
    font-size: 0.88rem;
    outline: none;
    height: var(--touch-min);
    box-sizing: border-box;
    transition: width 0.3s ease, border-color 0.25s ease, box-shadow 0.25s ease;
  }
  #navSearchInput::placeholder { color: var(--text-muted) !important; opacity: 0.8; }
  #navSearchInput:focus {
    width: 260px;
    border-color: var(--gold) !important;
    box-shadow: 0 0 0 3px rgba(201, 162, 39, 0.15) !important;
  }

  #navSearchDropdown {
    display: none;
    position: absolute;
    top: calc(100% + 8px);
    right: 0;
    width: 300px;
    max-height: 360px;
    overflow-y: auto;
    background: var(--surface) !important;
    border: 1px solid var(--border) !important;
    border-radius: 10px;
    box-shadow: 0 6px 20px var(--shadow) !important;
    z-index: 9999;
  }
  #navSearchDropdown.show { display: block; }

  /* ============================================================
     SEARCH RESULT ITEMS (shared by desktop dropdown + mobile results)
  ============================================================ */
  .nav-search-item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding: 10px 16px;
    min-height: var(--touch-min);
    font-size: 0.88rem;
    color: var(--text) !important;
    text-decoration: none;
    border-bottom: 1px solid var(--border) !important;
    background: transparent !important;
    -webkit-tap-highlight-color: transparent;
  }
  .nav-search-item:last-child { border-bottom: none; }
  .nav-search-item:hover, .nav-search-item:active {
    background-color: rgba(201, 162, 39, 0.08) !important;
    color: var(--gold-bright) !important;
    text-decoration: none;
  }
  .nav-search-item .parent-label { font-size: 0.72rem; color: var(--text-muted) !important; display: block; }
  .nav-search-item .child-label  { font-weight: 600; display: block; }
  .nav-search-no-results { padding: 14px 16px; color: var(--text-muted) !important; font-size: 0.85rem; text-align: center; }

  /* ============================================================
     MOBILE (≤ 600px)
  ============================================================ */
  @media (max-width: 600px) {
    #navSearchInput         { display: none !important; }
    #navSearchDropdown      { display: none !important; }
    #mobileSearchTrigger    { display: flex !important; }

    #divTitle {
      font-size: 0.82rem;
      max-width: none;
      flex: 1;
      min-width: 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .main-header.navbar > .d-flex { flex: 1; min-width: 0; }
    .main-header.navbar            { padding: 0 6px; }

    .nav-icon-btn {
      width: 34px !important;
      height: 34px !important;
      min-width: 34px !important;
      font-size: 0.82rem !important;
    }

    .nav-home-link  { font-size: 0.80rem !important; padding: 0 3px !important; }
    #navbarDropdown { font-size: 0.80rem; padding: 0 3px; }
    .navbar-nav     { gap: 2px !important; }

    /* theme toggle label hides on very small screens to save space */
    #themeToggleLabel { display: none; }
  }

  @media (min-width: 601px) {
    #mobileSearchTrigger    { display: none !important; }
    #mobileSearchOverlay    { display: none !important; }
    #mobileSearchResults    { display: none !important; }
  }

  /* ============================================================
     MOBILE SEARCH FULL-SCREEN OVERLAY
  ============================================================ */
  #mobileSearchOverlay {
    display: none;
    position: fixed;
    top: 0; left: 0; right: 0;
    z-index: 10500;
    background: var(--surface);
    border-bottom: 2px solid var(--gold);
    padding: 6px 8px;
    align-items: center;
    gap: 8px;
    box-shadow: 0 3px 14px var(--shadow);
  }
  #mobileSearchOverlay.active { display: flex; }

  #mobileSearchInput {
    flex: 1;
    border: 1.5px solid var(--gold);
    border-radius: 22px;
    padding: 8px 14px;
    font-size: 16px; /* prevents iOS auto-zoom on focus */
    outline: none;
    height: var(--touch-min);
    background: var(--bg);
    color: var(--text);
    -webkit-appearance: none;
  }
  #mobileSearchInput:focus {
    border-color: var(--gold-bright);
    box-shadow: 0 0 0 2px rgba(201,162,39,0.25);
  }

  #mobileSearchClose {
    background: none;
    border: none;
    color: var(--gold-dim);
    font-size: 1.2rem;
    width: var(--touch-min);
    height: var(--touch-min);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    flex-shrink: 0;
    -webkit-tap-highlight-color: transparent;
    touch-action: manipulation;
  }

  #mobileSearchResults {
    display: none;
    position: fixed;
    top: 56px; left: 0; right: 0;
    z-index: 10400;
    background: var(--surface);
    border-top: 1px solid var(--border);
    max-height: calc(100vh - 60px);
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
    box-shadow: 0 4px 20px var(--shadow);
  }
  #mobileSearchResults.show { display: block; }

  /* ============================================================
     MENU BROWSER MODAL — Desktop
  ============================================================ */
  #menuBrowserModal .modal-dialog {
    min-width: 88% !important;
    max-width: 1200px;
    margin: 28px auto;
  }
  #menuBrowserModal .modal-content {
    border-radius: 14px;
    border: 2px solid var(--gold);
    background: var(--surface);
  }
  #menuBrowserModal .modal-header {
    background: linear-gradient(135deg, var(--gold) 0%, var(--gold-dim) 100%);
    border-radius: 12px 12px 0 0;
    padding: 14px 22px;
    border-bottom: none;
  }
  #menuBrowserModal .modal-title {
    color: #fff; font-weight: 700; font-size: 1.05rem; letter-spacing: 0.03em;
  }
  #menuBrowserModal .modal-header .close {
    color: #fff; opacity: 0.85; font-size: 1.5rem; text-shadow: none;
    width: var(--touch-min); height: var(--touch-min);
    display: flex; align-items: center; justify-content: center;
    -webkit-tap-highlight-color: transparent;
  }
  #menuBrowserModal .modal-header .close:hover { opacity: 1; }
  #menuBrowserModal .modal-body {
    padding: 0;
    max-height: 76vh;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
  }

  /* ============================================================
     MENU BROWSER MODAL — Mobile bottom-sheet
  ============================================================ */
  @media (max-width: 600px) {
    #menuBrowserModal .modal-dialog {
      min-width: 100% !important;
      max-width: 100% !important;
      width: 100% !important;
      margin: 0 !important;
      position: fixed !important;
      bottom: 0 !important;
      top: auto !important;
      left: 0 !important;
      right: 0 !important;
    }
    #menuBrowserModal .modal-content {
      border-radius: 20px 20px 0 0 !important;
      border-left: none !important;
      border-right: none !important;
      border-bottom: none !important;
      max-height: 88vh;
      display: flex;
      flex-direction: column;
    }
    #menuBrowserModal .modal-header {
      flex-direction: column;
      align-items: center;
      padding-top: 10px;
      border-radius: 20px 20px 0 0;
    }
    #menuBrowserModal .modal-header::before {
      content: '';
      display: block;
      width: 40px; height: 5px;
      background: rgba(255,255,255,0.4);
      border-radius: 3px;
      margin-bottom: 8px;
    }
    .mb-header-row {
      display: flex;
      align-items: center;
      justify-content: space-between;
      width: 100%;
    }
    #menuBrowserModal .modal-body {
      flex: 1;
      max-height: none !important;
      overflow-y: auto !important;
      -webkit-overflow-scrolling: touch;
    }
    .menu-browser-grid {
      grid-template-columns: 1fr !important;
      gap: 10px !important;
      padding: 10px 12px 32px !important;
    }
    .menu-parent-header {
      padding: 14px !important;
      min-height: 52px;
    }
    .menu-parent-header .parent-name { font-size: 0.97rem !important; }
    .menu-child-link {
      min-height: 48px !important;
      padding: 13px 16px !important;
      font-size: 0.93rem !important;
    }
    #menuBrowserSearch {
      font-size: 16px !important;
      height: 46px !important;
    }
  }

  /* ============================================================
     MENU BROWSER — search bar
  ============================================================ */
  .menu-browser-search-bar {
    padding: 12px 16px 8px;
    background: var(--surface);
    position: sticky;
    top: 0;
    z-index: 10;
    border-bottom: 1px solid var(--border);
  }
  #menuBrowserSearch {
    width: 100%;
    border: 1.5px solid var(--border);
    border-radius: 22px;
    padding: 9px 16px;
    font-size: 0.92rem;
    outline: none;
    background: var(--bg);
    color: var(--text);
    height: var(--touch-min);
    box-sizing: border-box;
    -webkit-appearance: none;
  }
  #menuBrowserSearch:focus {
    border-color: var(--gold);
    box-shadow: 0 0 0 2px rgba(201,162,39,0.2);
  }

  /* ============================================================
     MENU BROWSER — grid & cards
  ============================================================ */
  .menu-browser-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 14px;
    padding: 16px 18px 24px;
  }
  .menu-parent-card {
    background: var(--surface);
    border: 2px solid var(--border);
    border-radius: 10px;
    overflow: hidden;
    transition: box-shadow 0.2s, border-color 0.2s;
  }
  .menu-parent-card:hover, .menu-parent-card.open {
    border-color: var(--gold);
    box-shadow: 0 4px 18px rgba(201,162,39,0.22);
  }
  .menu-parent-header {
    background: linear-gradient(135deg, var(--gold-soft, var(--gold-bright)) 0%, var(--gold) 100%);
    padding: 11px 14px;
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    user-select: none;
    min-height: var(--touch-min);
    -webkit-tap-highlight-color: transparent;
    touch-action: manipulation;
  }
  .menu-parent-header .parent-icon {
    width: 30px; height: 30px;
    background: var(--gold-dim);
    border-radius: 6px;
    display: flex; align-items: center; justify-content: center;
    flex-shrink: 0;
  }
  .menu-parent-header .parent-icon i { color: #fff; font-size: 0.75rem; }
  .menu-parent-header .parent-name {
    font-weight: 700; font-size: 0.9rem; color: #2a1e00; flex: 1; line-height: 1.3;
  }
  .child-count-badge {
    background: var(--gold-dim); color: #fff;
    font-size: 0.68rem; font-weight: 700;
    border-radius: 10px; padding: 2px 8px; flex-shrink: 0;
  }
  .menu-parent-header .toggle-icon {
    font-size: 0.8rem; color: #4a3600;
    transition: transform 0.25s ease;
    flex-shrink: 0; width: 18px; text-align: center;
  }
  .menu-parent-card.open .toggle-icon { transform: rotate(180deg); }

  .menu-children-list {
    max-height: 0; overflow: hidden;
    transition: max-height 0.3s ease;
    background: var(--surface);
  }
  .menu-parent-card.open .menu-children-list { max-height: 900px; }

  .menu-child-link {
    display: flex; align-items: center; gap: 10px;
    padding: 10px 14px;
    min-height: var(--touch-min);
    font-size: 0.86rem; color: var(--text);
    text-decoration: none;
    border-bottom: 1px solid var(--border);
    transition: background 0.15s, color 0.15s;
    -webkit-tap-highlight-color: transparent;
    touch-action: manipulation;
  }
  .menu-child-link:last-child { border-bottom: none; }
  .menu-child-link i { color: var(--gold); font-size: 0.75rem; flex-shrink: 0; }
  .menu-child-link:hover, .menu-child-link:active {
    background: rgba(201,162,39,0.08); color: var(--gold-dim); text-decoration: none;
  }

  .menu-browser-no-results {
    text-align: center; padding: 40px 20px;
    color: var(--text-muted); font-size: 0.9rem; grid-column: 1 / -1;
  }
  .menu-browser-no-results i { display: block; font-size: 2rem; margin-bottom: 10px; color: var(--border); }

  /* ============================================================
     FORM CONTROLS / CARDS — theme adaptation (kept from Auric theme)
  ============================================================ */
  .form-control, input.form-control, select.form-control, textarea.form-control, input[type="text"], input[type="textbox"] {
    background-color: var(--bg) !important;
    color: var(--text) !important;
    border: 1px solid var(--border) !important;
  }
  .form-control:focus, input[type="text"]:focus, input[type="textbox"]:focus {
    border-color: var(--gold) !important;
    box-shadow: 0 0 0 3px rgba(201, 162, 39, 0.15) !important;
  }
  .form-control:disabled, input[type="text"]:disabled, input[type="textbox"]:disabled {
    background-color: var(--border) !important;
    opacity: 0.6;
  }
  .card {
    background-color: var(--surface) !important;
    color: var(--text) !important;
    border: 1px solid var(--border) !important;
  }

  [style*="background-color:white"], [style*="background-color: white"],
  [style*="background-color:#fff"], [style*="background-color: #fff"],
  [style*="background-color:#ffffff"], [style*="background-color: #ffffff"],
  [style*="background:white"], [style*="background: white"],
  [style*="background:#fff"], [style*="background: #fff"],
  [style*="background:#ffffff"], [style*="background: #ffffff"] {
    background-color: var(--surface) !important;
    background: var(--surface) !important;
    color: var(--text) !important;
  }

  [data-theme="dark"] [style*="color:black"], [data-theme="dark"] [style*="color: black"],
  [data-theme="dark"] [style*="color:#000"], [data-theme="dark"] [style*="color: #000"],
  [data-theme="dark"] [style*="color:#333"], [data-theme="dark"] [style*="color: #333"],
  [data-theme="dark"] [style*="color:#495057"], [data-theme="dark"] [style*="color: #495057"] {
    color: var(--text) !important;
  }

  [data-theme="dark"] .table-container,
  [data-theme="dark"] .plan-date-filter-bar {
    background-color: var(--surface) !important;
    border: 1px solid var(--border) !important;
    color: var(--text) !important;
  }
  [data-theme="dark"] .table th, [data-theme="dark"] table th {
    background-color: var(--gold) !important;
    color: #fff !important;
  }
  [data-theme="dark"] .table td, [data-theme="dark"] table td {
    background-color: transparent !important;
    color: var(--text) !important;
  }
  [data-theme="dark"] .section-divider span,
  [data-theme="dark"] .section-collapse-toggle,
  [data-theme="dark"] .plan-date-filter-row .plan-date-label {
    color: var(--text) !important;
  }
  [data-theme="dark"] .table {
    --bs-table-bg: var(--surface) !important;
    --bs-table-striped-bg: rgba(255, 255, 255, 0.03) !important;
    color: var(--text) !important;
  }
  [data-theme="dark"] .org-box {
    background: var(--surface) !important;
    border: 1px solid var(--border) !important;
    color: var(--text) !important;
  }
  [data-theme="dark"] .org-box:hover { background-color: rgba(201, 162, 39, 0.08) !important; }
  [data-theme="dark"] .org-role { color: var(--text-muted) !important; }
  [data-theme="dark"] .org-name { color: var(--text) !important; }

  [data-theme="dark"] .menu-parent-header .parent-name { color: #1a1300; }
  [data-theme="dark"] .menu-parent-header .toggle-icon { color: #2a1e00; }

  .dropdown-menu { background-color: var(--surface) !important; border: 1px solid var(--border) !important; }
  .dropdown-item { color: var(--text) !important; }
  .dropdown-item:hover { background-color: rgba(201, 162, 39, 0.12) !important; color: var(--gold-bright) !important; }

  /* ============================================================
     THEME TOGGLE SWITCH
  ============================================================ */
  .switch {
    position: relative;
    width: 52px;
    height: 28px;
    border-radius: 20px;
    border: 1px solid var(--border);
    background: var(--bg);
    cursor: pointer;
    flex-shrink: 0;
    transition: background 0.3s ease, border-color 0.3s ease;
    display: inline-block;
  }
  .switch .knob {
    position: absolute;
    top: 2px; left: 2px;
    width: 22px; height: 22px;
    border-radius: 50%;
    background: linear-gradient(135deg, var(--gold-bright), var(--gold));
    box-shadow: 0 1px 4px var(--shadow);
    transition: transform 0.32s cubic-bezier(.4,0,.2,1);
    display: flex; align-items: center; justify-content: center;
    font-size: 11px;
  }
  [data-theme="dark"] .switch .knob { transform: translateX(24px); }
</style>
</head>

<body class="hold-transition layout-fixed">
<div class="wrapper">

<!-- ============================================================
     MOBILE SEARCH OVERLAY
============================================================ -->
<div id="mobileSearchOverlay">
  <input type="search" id="mobileSearchInput" placeholder="Search menu..." autocomplete="off" />
  <button id="mobileSearchClose" aria-label="Close"><i class="fas fa-times"></i></button>
</div>
<div id="mobileSearchResults"></div>

<!-- ============================================================
     NAVBAR
============================================================ -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light" style="margin-left:0">

  <div class="d-flex align-items-center" style="min-width:0; overflow:hidden; flex:1;">
    <div id="divTitle" class="ml-2"></div>
  </div>

  <ul class="navbar-nav ml-auto align-items-center" style="flex-wrap:nowrap; gap:4px; flex-shrink:0;">

    <!-- Desktop search (hidden on mobile via CSS) -->
    <li class="nav-item mr-2" id="navSearchWrapper">
      <input
        type="text"
        id="navSearchInput"
        placeholder="&#xf002; Search menu..."
        autocomplete="off"
        style="font-family:'Font Awesome 5 Free','Segoe UI',sans-serif;"
      />
      <div id="navSearchDropdown"></div>
    </li>

    <!-- Mobile search icon (shown on mobile only) -->
    <li class="nav-item" id="mobileSearchTrigger" style="display:none">
      <button class="nav-icon-btn" onclick="openMobileSearch()" aria-label="Search">
        <i class="fas fa-search"></i>
      </button>
    </li>

    <!-- Menu browser (all sizes) -->
    <li class="nav-item">
      <button class="nav-icon-btn" onclick="openMenuBrowser()" title="Browse menus" aria-label="Browse menus">
        <i class="fas fa-th"></i>
      </button>
    </li>

    <!-- THEME TOGGLE -->
    <li class="nav-item d-flex align-items-center mr-2 ml-2">
      <span id="themeToggleLabel" style="font-size: 13px; color: var(--text); font-weight: 500; margin-right: 8px;">Dark Mode</span>
      <div class="switch" id="themeSwitch">
        <div class="knob" id="knob"></div>
      </div>
    </li>

    <!-- Home -->
    <li class="nav-item">
      <a href="?a=showHomePage" class="nav-link nav-home-link"><strong>Home</strong> <span style="color:green;font-weight:800">${userdetails.firm_name}</span></a>
    </li>

    <!-- User dropdown -->
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" style="padding:0 6px">
        <strong>${userdetails.username}</strong> 
      </a>
      <div class="dropdown-menu dropdown-menu-right">
        <a class="dropdown-item"><strong>Valid Till (${userdetails.validTillDDMMYYY})</strong></a>
        <a href="?a=showChangePassword" class="dropdown-item"><strong>Change Password</strong></a>
        <a href="?a=showShortcuts" class="dropdown-item"><strong>Shortcuts</strong></a>
        <a href="javascript:copyDebugInfo()" class="dropdown-item"><strong>Copy Debug Info</strong></a>
        <a href="javascript:logout();" class="dropdown-item" id="refLogout"><strong>Logout</strong></a>
      </div>
    </li>

  </ul>
</nav>

<!-- ============================================================
     MENU DATA
============================================================ -->
<script>
var menuItems = [
  <c:forEach items="${elementsDB}" var="item">
    <c:forEach items="${item.getChildElements()}" var="item1">
      { parent: "${item.getElementName()}", child: "${item1.getElementName()}", url: "${item1.getElementUrl()}" },
    </c:forEach>
  </c:forEach>
];
</script>

<!-- ============================================================
     MAIN CONTENT
============================================================ -->
<div class="content-wrapper" style="background:lightblue">
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <jsp:include page="${jspName}"/>
        </div>
      </div>
    </div>
  </section>
</div>

</div><!-- /wrapper -->

<!-- ============================================================
     MODAL — generic/logout
============================================================ -->
<div class="modal fade" id="myModal" role="dialog">
  <div class="modal-dialog modal-lg" style="min-width:100%">
    <div class="modal-content">
      <div class="modal-body" align="center">
        <p id="responseText"><div align="center" class="loader" id="loader"></div></p>
      </div>
      <div class="modal-footer" style="text-align:right">
        <button id="closebutton" type="button" onclick='location.reload()' class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- ============================================================
     MENU BROWSER MODAL
============================================================ -->
<div class="modal fade" id="menuBrowserModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">

      <div class="modal-header">
        <div class="mb-header-row">
          <h5 class="modal-title" style="margin:0">
            <i class="fas fa-compass mr-2"></i>Browse All Menus
          </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
      </div>

      <div class="modal-body">
        <div class="menu-browser-search-bar">
          <input
            type="search"
            id="menuBrowserSearch"
            placeholder="&#xf002;  Filter menus..."
            autocomplete="off"
            style="font-family:'Font Awesome 5 Free','Segoe UI',sans-serif;"
          />
        </div>
        <div class="menu-browser-grid" id="menuBrowserGrid"></div>
      </div>

    </div>
  </div>
</div>

<!-- ============================================================
     JAVASCRIPT
============================================================ -->
<script>
function navigateToURL(u) { window.location = u; }

/* ---- HTML escape ---- */
function escHtml(s) {
  return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}
function highlightMatch(text, q) {
  var i = text.toLowerCase().indexOf(q);
  if (i < 0) return escHtml(text);
  return escHtml(text.slice(0,i))
    + '<mark style="background:#fff3b0;padding:0">' + escHtml(text.slice(i, i+q.length)) + '</mark>'
    + escHtml(text.slice(i+q.length));
}

/* ---- Build result list into any container (desktop dropdown or mobile results) ---- */
function buildSearchResults(query, container) {
  var q = (query||'').trim().toLowerCase();
  container.innerHTML = '';
  if (!q) { container.classList.remove('show'); return; }
  var matched = menuItems.filter(function(m){
    return (m.parent+' '+m.child).toLowerCase().includes(q);
  });
  if (!matched.length) {
    container.innerHTML = '<div class="nav-search-no-results">No results found</div>';
  } else {
    matched.forEach(function(m){
      var a = document.createElement('a');
      a.href = m.url; a.className = 'nav-search-item';
      a.innerHTML = '<span class="parent-label">'+escHtml(m.parent)+'</span>'
                  + '<span class="child-label">'+highlightMatch(m.child,q)+'</span>';
      container.appendChild(a);
    });
  }
  container.classList.add('show');
}

/* ---- Logout ---- */
function logout() {
  document.getElementById("closebutton").style.display = 'none';
  $('#myModal').modal({backdrop:'static', keyboard:false});
  var x = new XMLHttpRequest();
  x.onreadystatechange = function(){
    if (x.readyState==4 && x.status==200){
      document.getElementById("responseText").innerHTML = x.responseText;
      document.getElementById("closebutton").style.display = 'block';
      window.location.reload();
    }
  };
  x.open("GET","?a=Logout",true); x.send();
}
$(document).ready(function(){ window.onkeydown = function(e){ if(e.keyCode==119) logout(); }; });
</script>

<!-- Desktop navbar search -->
<script>
(function(){
  var input = document.getElementById('navSearchInput');
  var drop  = document.getElementById('navSearchDropdown');
  if (!input) return;

  input.addEventListener('input', function(){ buildSearchResults(this.value, drop); });
  input.addEventListener('focus', function(){ if(this.value.trim()) buildSearchResults(this.value, drop); });

  document.addEventListener('click', function(e){
    var w = document.getElementById('navSearchWrapper');
    if (w && !w.contains(e.target)) drop.classList.remove('show');
  });

  input.addEventListener('keydown', function(e){
    var items  = drop.querySelectorAll('.nav-search-item');
    var active = drop.querySelector('.nav-search-item.active-result');
    var idx=-1; items.forEach(function(el,i){ if(el===active) idx=i; });
    function activate(n){
      if(active){ active.classList.remove('active-result'); active.style.background=''; }
      if(items[n]){ items[n].classList.add('active-result'); items[n].style.background='rgba(201,162,39,0.12)'; }
    }
    if      (e.key==='ArrowDown') { e.preventDefault(); activate(idx+1<items.length?idx+1:0); }
    else if (e.key==='ArrowUp')   { e.preventDefault(); activate(idx-1>=0?idx-1:items.length-1); }
    else if (e.key==='Enter')     { if(active) window.location=active.href; }
    else if (e.key==='Escape')    { drop.classList.remove('show'); }
  });
})();
</script>

<!-- Mobile search overlay -->
<script>
(function(){
  var overlay  = document.getElementById('mobileSearchOverlay');
  var minput   = document.getElementById('mobileSearchInput');
  var mresults = document.getElementById('mobileSearchResults');
  var closeBtn = document.getElementById('mobileSearchClose');

  window.openMobileSearch = function(){
    overlay.classList.add('active');
    mresults.innerHTML = ''; mresults.classList.remove('show');
    setTimeout(function(){ minput.focus(); }, 80);
  };
  function closeMobileSearch(){
    overlay.classList.remove('active');
    mresults.classList.remove('show');
    minput.value = ''; minput.blur();
  }
  closeBtn.addEventListener('click', closeMobileSearch);
  minput.addEventListener('input', function(){ buildSearchResults(this.value, mresults); });
  mresults.addEventListener('click', function(e){
    if (e.target.closest('.nav-search-item')) closeMobileSearch();
  });
  document.addEventListener('touchstart', function(e){
    if (overlay.classList.contains('active')
        && !overlay.contains(e.target)
        && !mresults.contains(e.target)) closeMobileSearch();
  }, {passive:true});
})();
</script>

<!-- Menu browser -->
<script>
function groupMenuItems(items){
  var g={}, o=[];
  items.forEach(function(m){ if(!g[m.parent]){ g[m.parent]=[]; o.push(m.parent); } g[m.parent].push(m); });
  return {groups:g, order:o};
}

function buildMenuBrowserGrid(filterText){
  var grid = document.getElementById('menuBrowserGrid');
  grid.innerHTML = '';
  var q    = (filterText||'').trim().toLowerCase();
  var data = groupMenuItems(menuItems);
  var any  = false;

  data.order.forEach(function(parent){
    var kids = data.groups[parent];
    var vis  = q ? kids.filter(function(c){ return c.child.toLowerCase().includes(q)||c.parent.toLowerCase().includes(q); }) : kids;
    if (!vis.length) return;
    any = true;

    var card = document.createElement('div');
    card.className = 'menu-parent-card'+(q?' open':'');

    var hdr = document.createElement('div');
    hdr.className = 'menu-parent-header';
    hdr.setAttribute('role','button');
    hdr.innerHTML =
      '<div class="parent-icon"><i class="fas fa-folder"></i></div>'+
      '<span class="parent-name">'+escHtml(parent)+'</span>'+
      '<span class="child-count-badge">'+vis.length+'</span>'+
      '<span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>';
    hdr.addEventListener('click', function(){ card.classList.toggle('open'); });

    var list = document.createElement('div');
    list.className = 'menu-children-list';
    vis.forEach(function(c){
      var a = document.createElement('a');
      a.href = c.url; a.className = 'menu-child-link';
      a.innerHTML = '<i class="fas fa-angle-right"></i>'+escHtml(c.child);
      list.appendChild(a);
    });

    card.appendChild(hdr); card.appendChild(list);
    grid.appendChild(card);
  });

  if (!any) {
    grid.innerHTML = '<div class="menu-browser-no-results"><i class="fas fa-search"></i>No menus found for <strong>&ldquo;'+escHtml(filterText)+'&rdquo;</strong></div>';
  }
}

function openMenuBrowser(){
  var s = document.getElementById('menuBrowserSearch');
  s.value = '';
  buildMenuBrowserGrid('');
  $('#menuBrowserModal').modal('show');
  $('#menuBrowserModal').one('shown.bs.modal', function(){
    if (window.innerWidth >= 601) s.focus();
  });
}

document.addEventListener('DOMContentLoaded', function(){
  var s = document.getElementById('menuBrowserSearch');
  if (s) s.addEventListener('input', function(){ buildMenuBrowserGrid(this.value); });
});
</script>

<!-- Keyboard shortcuts -->
<script>
window.addEventListener('keydown', function(e){
  if (e.altKey && e.keyCode==76)                              logout();                         // ALT+L
  if (e.altKey && e.shiftKey && e.keyCode==82)                window.location.href="?a=reloadSession"; // ALT+SHIFT+R
  if (e.ctrlKey && e.shiftKey && e.key==='F')  { var n=document.getElementById('navSearchInput'); if(n) n.focus(); } // CTRL+SHIFT+F
  if (e.ctrlKey && e.shiftKey && e.key==='H')                 window.location="?a=showHomePage";       // CTRL+SHIFT+H
  if (e.ctrlKey && e.shiftKey && e.key==='L')                 logout();                                // CTRL+SHIFT+L
  if (e.ctrlKey && e.shiftKey && e.key==='M')                 openMenuBrowser();                        // CTRL+SHIFT+M
});

window.onload = function(){
  var isAndroid = /Android/i.test(navigator.userAgent);
  var isMobile  = window.innerWidth <= 600;
  if (!isAndroid && !isMobile){
    var n = document.getElementById('navSearchInput');
    if (n) n.focus();
  }
};
</script>

<!-- Debug info helpers -->
<script>
function getUserRoles(callback) {
  $.ajax({
    url: "?a=getUserRoles",
    method: "GET",
    success: function(res) { callback(res.roles || []); },
    error: function() { callback(["unknown"]); }
  });
}

function getDebugInfo(callback) {
  $.ajax({
    url: "?a=getDebugInfo",
    method: "GET",
    success: function(res) {
      if (typeof res === "string") {
        try { res = JSON.parse(res); } catch (e) { console.error("JSON parse failed", e); }
      }
      callback(res);
    },
    error: function(err) {
      console.error("AJAX ERROR", err);
      callback({ roles: ["unknown"], environment: "unknown" });
    }
  });
}

function getNavigationPath() {
  return document.title || "Unknown Path";
}

function copyDebugInfo(errorMsg = "") {
  getDebugInfo(function(res) {
    const roles = (res && res.roles) ? res.roles : [];
    const env = (res && res.environment) ? res.environment : "unknown";

    const data =
      "Login User : \"${userdetails.username}\"\n" +
      "Current roles with user : \"" + roles.join(", ") + "\"\n" +
      "Exact Navigation Path for the screen : \"" + getNavigationPath() + "\"\n" +
      "Issue occured in which environment : \"" + env + "\"\n" +
      "Reproducible steps : \n" +
      "Date time of error/Problem occured : \"" + new Date().toLocaleString() + "\"\n" +
      "URL : \"" + window.location.href + "\"\n" +
      "Error : \"" + (errorMsg || "N/A") + "\"\n" +
      "Browser : \"" + navigator.userAgent + "\"\n";

    copyToClipboard(data);
  });
}

function copyToClipboard(text) {
  if (navigator.clipboard && window.isSecureContext) {
    navigator.clipboard.writeText(text)
      .then(() => toastr.success("Debug info copied"))
      .catch(() => fallbackCopy(text));
  } else {
    fallbackCopy(text);
  }
}

function fallbackCopy(text) {
  const textarea = document.createElement("textarea");
  textarea.value = text;
  textarea.style.position = "fixed";
  textarea.style.left = "-9999px";
  document.body.appendChild(textarea);
  textarea.focus();
  textarea.select();
  try {
    document.execCommand("copy");
    toastr.success("Debug info copied");
  } catch (err) {
    toastr.error("Copy failed");
    console.error("Copy failed", err);
  }
  document.body.removeChild(textarea);
}
</script>

<!-- Global theme switch -->
<script>
(function() {
  const switchEl = document.getElementById('themeSwitch');
  const STORAGE_KEY = 'auric-theme-home';

  function applyTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme);
    document.querySelectorAll('body, .content-wrapper, .wrapper').forEach(el => {
      el.setAttribute('data-theme', theme);
    });
  }

  try {
    const saved = localStorage.getItem(STORAGE_KEY);
    applyTheme(saved === 'dark' || saved === 'light' ? saved : 'light');
  } catch (e) {
    console.warn('Theme could not be loaded from localStorage:', e);
    applyTheme('light');
  }

  if (switchEl) {
    switchEl.addEventListener('click', () => {
      const isDark = document.documentElement.getAttribute('data-theme') === 'dark';
      const next = isDark ? 'light' : 'dark';
      applyTheme(next);
      try { localStorage.setItem(STORAGE_KEY, next); } catch (e) { console.warn('Theme could not be saved:', e); }
      window.dispatchEvent(new Event('themechange'));
    });
  }
})();
</script>

</body>
</html>
