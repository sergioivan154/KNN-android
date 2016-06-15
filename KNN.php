<?php

/**
 * Representa el la estructura de las metas
 * almacenadas en la base de datos
 */


class KNN
{
    

    function __construct()
    {
        ini_set('memory_limit', '1024M');
    }

    /**
     * Retorna en la fila especificada de la tabla 'meta'
     *
     * @param $idMeta Identificador del registro
     * @return array Datos del registro
     */
    public static function calcularClase($x,$y,$z, $k)
    {
        $file = fopen("2.csv","r");
        $i = 0;

        while(! feof($file))
        {

            $arrayCVS[$i++] = fgetcsv($file);

        }
        
        fclose($file);
        return KNN::clasificar($arrayCVS,$x,$y,$z,$k);
    }

    private static function entrenar(){

    }

    private static function clasificar($banco, $x,$y,$z,$k){
        $clase = 0;

        for ($i = 0; $i<sizeof($banco); $i++){
            $d[$i] = KNN::distancia($x,$banco[$i][0],$y,$banco[$i][1], $z,$banco[$i][2], $banco[$i][3] );    
        }
        

        $clases = array();
        $distancias = array();
        // Obtener una lista de columnas
        foreach ($d as $clave => $fila) {
            $distancias[$clave] = $fila['distancia'];
            $clases[$clave] = $fila['clase'];
        }
        

        // Ordenar los datos con volumen descendiente, edición ascendiente
        // Agregar $d como el último parámetro, para ordenar por la clave común
        array_multisort($distancias, SORT_ASC, $clases, SORT_ASC, $d);

        $knnDataSet = array();
        for($i = 0; $i<$k; $i++){
            $knnDataSet[$i] = $clases[$i];
        }

        $knn = array_count_values($knnDataSet);
        
        
        $clasesKnn = array();
        $insidencias = array();

        $i = 0;
        foreach ($knn  as $key => $value)  {
            $clasesKnn[$i] = $key;            
            $insidencias[$i++] = $value;
        }

        // Ordenar los datos con volumen descendiente, edición ascendiente
        // Agregar $d como el último parámetro, para ordenar por la clave común
        array_multisort($insidencias, SORT_DESC, $clasesKnn, SORT_ASC, $knn);
        $clase = $clasesKnn[0];

        

        return $clase;
    }

    private static function distancia($x1,$x2,$y1,$y2,$z1,$z2, $clase){

        $d = sqrt(pow(($x2 - $x1),2)+pow(($y2 - $y1),2)+pow(($z2 - $z1),2));
        
        $distancia = array('distancia' => $d, 'clase' => $clase);

        return $distancia;
    }




}

?>