package obra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import sistema.Sistema;

/**
 * Parser de las obras
 */
public class ObraParser {
	
	public static void guardarObras(String path) {
		String header = "TIPO\tORIGEN\tTITULO\tAUTOR\tAÑO\tDESCRIPCION\tCUANTIA_SEGURO\tNUMERO_POLIZA\tANCHURA_CM\tALTURA_CM\tPROFUNDIDAD_CM\tTEMPERATURA\tHUMEDAD\tTECNICA_CUADRO\tMATERIAL_ESCULTURA\tCOLOR_FOTOGRAFIA\tDURACION_AV	IDIOMA_AV\r\n";
		header.replace("\t", "\t\t");
		try (BufferedWriter b = new BufferedWriter(new FileWriter(path))) {
			b.write(header);
			
			for (Obra o : Sistema.getInstance().getObrasRegistradas().values()) {
				b.write(o.getFormatoFichero() + "\n");
			}

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static List<Obra> getObras(FileReader reader) {
		List<Obra> l = new ArrayList<>();
		
		try (BufferedReader b = new BufferedReader(reader)) {
            String linea;
            String[] ls;
            b.readLine();
            //(, , , , , ,
        	//		, , , double ancho, double largo, double alto, String t)
            while ((linea = b.readLine()) != null) {
                ls = linea.split("\\|");
                String[] temps = ls[11].split("--");
                String[] hum = ls[12].split("--");
                Obra oLeida;

                /*Caracteristicas de todas las obras*/
                boolean delCentro;
                String titulo;
            	String autorObra;
            	LocalDate anyo;
            	String descripcion;
            	Seguro seguro = new Seguro(Double.valueOf(ls[6]), ls[7]);
                
                if (ls[1].equals("EXTERNA"))
                	delCentro = false;
                else
                	delCentro = true;
                
                titulo = ls[2];
                autorObra = ls[3];
                anyo = LocalDate.parse(ls[4]);
                descripcion = ls[5];
                
                if (ls[0].equals("AUDIOVISUAL")) {
                	String idioma = ls[16];
                	LocalTime duracion = LocalTime.parse(ls[17]);
                	oLeida = new Audiovisual(titulo, autorObra, delCentro, anyo, descripcion, seguro, duracion, idioma);
                }
                else {
                	/*Caracteristicas de obras dimensionadas*/
                	double tempMin = Double.valueOf(temps[0]);
                	double tempMax = Double.valueOf(temps[1]);
                	double humMin = Double.valueOf(hum[0]);
                	double humMax = Double.valueOf(hum[1]);
                	
                	double ancho = Double.valueOf(ls[8]);
                	double largo = Double.valueOf(ls[9]);
                	double alto = Double.valueOf(ls[10]);
                	
                	if (ls[0].equals("CUADRO")) {
                		String tecnica = ls[13];
                		oLeida = new Cuadro(titulo, autorObra, delCentro, anyo, descripcion, seguro, tempMin, tempMax, humMin, humMax, ancho, largo, alto, tecnica);
                	}
                	else if (ls[0].equals("FOTO")) {
                		boolean color = Boolean.parseBoolean(ls[15]);
                		oLeida = new Foto(titulo, autorObra, delCentro, anyo, descripcion, seguro, tempMin, tempMax, humMin, humMax, ancho, largo, alto, color);
                	}
                	else {
                		String material = ls[14];
                		oLeida = new Escultura(titulo, autorObra, delCentro, anyo, descripcion, seguro, tempMin, tempMax, humMin, humMax, ancho, largo, alto, material);
                	}
                }
                l.add(oLeida);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return l;
	}
}