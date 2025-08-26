package app.killacode.back_app;

import app.killacode.back_app.model.Seccion;
import app.killacode.back_app.model.Teoria;
import app.killacode.back_app.repository.SeccionRepository;
import app.killacode.back_app.repository.TeoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BackAppApplicationTests {

	@Autowired
	private TeoriaRepository teoriaRepository;

	@Autowired
	private SeccionRepository seccionRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testGuardarTeoria() {
		Teoria teoria = new Teoria();
		teoria.setTitulo("Título de prueba");
		teoria.setId_teoria("t01");
		Teoria savedTeoria = teoriaRepository.save(teoria);
		assertThat(savedTeoria.getId_teoria()).isNotNull();
		assertThat(savedTeoria.getTitulo()).isEqualTo("Título de prueba");
	}

	@Test
	void testGuardarSeccion() {
		Seccion seccion = new Seccion();
		seccion.setHeader("Sección de prueba header");
		seccion.setFooter("Sección de prueba footer");
		seccion.setImage("Sección de prueba image");
		seccion.setBody("Sección de prueba body");
		seccion.setId_section("s01");
		Seccion savedSeccion = seccionRepository.save(seccion);
		assertThat(savedSeccion.getId_section()).isNotNull();
		assertThat(savedSeccion.getHeader()).isEqualTo("Sección de prueba header");
		assertThat(savedSeccion.getFooter()).isEqualTo("Sección de prueba footer");
		assertThat(savedSeccion.getImage()).isEqualTo("Sección de prueba image");
		assertThat(savedSeccion.getBody()).isEqualTo("Sección de prueba body");
	}
}