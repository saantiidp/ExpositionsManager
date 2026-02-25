package obra;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		Obra o = new Cuadro("Noche estrellada", "Vincent Van Gogh", false, LocalDate.of(1889, 1, 1), "representa la vista desde la ventana orientada al este de su habitación de asilo en Saint-Rémy-de-Provence, justo antes del amanecer, con la adición de un pueblo imaginario", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "oleo");
		Obra o1 = new Audiovisual("audiovi", "yo", false, LocalDate.now(), "desc", new Seguro(11, "456"), LocalTime.now(), "Frances");
		ObraDimensionada o2 = new Foto("titulo", "autor", true, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, true);
		ObraDimensionada o3 = new Escultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "material");
		System.out.println(o.getFormatoFichero());
		System.out.println(o1.getFormatoFichero());
		System.out.println(o2.getFormatoFichero());
		System.out.println(o3.getFormatoFichero());
		
		List<Obra> obras;
		try {
			FileReader reader = new FileReader("./obras.txt");
			obras = ObraParser.getObras(reader);
			
			for (Obra obb : obras) {
				System.out.println(obb);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
