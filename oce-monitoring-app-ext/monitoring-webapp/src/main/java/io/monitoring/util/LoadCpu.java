package io.monitoring.util;

public class LoadCpu {

	public LoadCpu() {}
	  
	
	/**
     * Starts the Load Generation
     */
    public void addLoad() {
    	// assume number of core 2
        int numCore = 2;
        // number of core
        int numThreadsPerCore = 2;
        double load = 0.8;
        final long duration = 1000;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new LoadThread("Thread" + thread, load, duration).start();
        }
    }

    private static class LoadThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public LoadThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
