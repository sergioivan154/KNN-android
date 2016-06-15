<?php
/**
 * Obtiene todas las metas de la base de datos
 */

require 'KNN.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar petición GET
    if (isset($_GET['x']) && isset($_GET['y']) && isset($_GET['z'])&& isset($_GET['k'])) {

        // Obtener parámetros
        $x = $_GET['x'];
        $y = $_GET['y'];
        $z = $_GET['z'];
        $k = $_GET['k'];
        // Tratar retorno
        $retorno = KNN::calcularClase($x, $y, $z, $k);

    }

    if ($retorno) {

        $datos["estado"] = 1;
        $datos["clase"] = $retorno;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}