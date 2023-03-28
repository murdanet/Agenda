import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Agenda {
    private List<Tasca> tasques;
    private boolean sortir;
    private ScheduledExecutorService executor;

    public Agenda() {
        this.tasques = new ArrayList<>();
        this.sortir = false;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public synchronized boolean afegeixTasca(Tasca tasca) {
        if (tasca.getHoraExecucio().isBefore(LocalTime.now())) {
            return false;
        }
        tasques.add(tasca);
        return true;
    }

    public synchronized Tasca getProperaTasca() {
        if (tasques.isEmpty()) {
            return null;
        }
        Tasca properaTasca = tasques.get(0);
        for (Tasca tasca : tasques) {
            if (tasca.getHoraExecucio().isBefore(properaTasca.getHoraExecucio())) {
                properaTasca = tasca;
            }
        }
        tasques.remove(properaTasca);
        return properaTasca;
    }

    public void run() {
        executor.scheduleAtFixedRate(() -> {
            Tasca properaTasca = getProperaTasca();
            if (properaTasca != null) {
                long tempsRestant = LocalTime.now().until(properaTasca.getHoraExecucio(), TimeUnit.SECONDS.toChronoUnit());
                if (tempsRestant < 2) {
                    System.out.println(properaTasca.getNom() + " executa");
                } else {
                    afegeixTasca(properaTasca);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
        while (!sortir) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    public void setSortir(boolean sortir) {
        this.sortir = sortir;
    }
}
