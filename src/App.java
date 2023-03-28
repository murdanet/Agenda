import java.time.LocalTime;

public class App {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        // crear 10 tasques a partir dels 10 segons i cada 5
        for (int i = 0; i < 10; i++) {
            Tasca tasca = new Tasca(LocalTime.now().plusSeconds(10 + i * 5), "Tasca (" + i + ")");
            agenda.afegeixTasca(tasca);
            System.out.println("Tasca (" + i + ") afegida per a les " + tasca.getHoraExecucio());
        }

        System.out.println("Inici: " + LocalTime.now());
        agenda.run();
        System.out.println("Completat");
    }
}
