package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GroupeThread {

    // 1 thread unique partagé par tous les services (écriture en base)
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // 2 threads en parallèle pour les traitements lourds(pdf, courriel, exports)
    private static final ExecutorService exLourd = Executors.newFixedThreadPool(2);

    // permet aux services de récupérer le thread pour lui donner des tâches
    public static ExecutorService getExecutor() {
        return executor;
    }

    public static ExecutorService getExLourd() {
        return exLourd;
    }

    // fermer les thread à la fin du programme (1 fois)
    public static void fermer() throws InterruptedException {
        // n'accepte plus de  nouvelles tâches
        executor.shutdown();
        exLourd.shutdown();
        // force l'arrêt au bout de 5s
        if(!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        if(!exLourd.awaitTermination(5, TimeUnit.SECONDS)) {
            exLourd.shutdownNow();
        }
    }
}
