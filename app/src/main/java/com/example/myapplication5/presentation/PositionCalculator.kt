package com.example.myapplication5.presentation

/**
 * A class responsible for calculating position based on accelerometer and gyroscope data.
 */
class PositionCalculator {
    private val position = FloatArray(3) { 0f } // Array to hold position values in x, y, z.
    private val velocity = FloatArray(3) { 0f } // Array to hold velocity values in x, y, z.
    private val kalmanFilter = KalmanFilter() // KalmanFilter instance for sensor data fusion.

    /**
     * Calculates the position based on accelerometer and gyroscope data.
     *
     * @param accelerometerData List of accelerometer data arrays.
     * @param gyroscopeData List of gyroscope data arrays.
     * @return List of fused position data arrays.
     */
    fun calculatePosition(accelerometerData: List<FloatArray>, gyroscopeData: List<FloatArray>): List<FloatArray> {
        val positions = mutableListOf<FloatArray>()
        val fusedData = fuseSensorData(accelerometerData, gyroscopeData)
        if (fusedData.isEmpty()) return positions

        var lastTime = fusedData[0][0]

        for (data in fusedData) {
            val currentTime = data[0]
            val dt = currentTime - lastTime
            if (dt > 0) {
                lastTime = currentTime
                val acc = data.sliceArray(1..3)

                for (i in 0..2) {
                    velocity[i] += acc[i] * dt
                    position[i] += velocity[i] * dt
                }

                positions.add(floatArrayOf(currentTime, position[0], position[1], position[2]))
            }
        }

        return positions
    }


    /**
     * Fuses accelerometer and gyroscope data using a Kalman filter.
     *
     * @param accelerometerData List of accelerometer data arrays.
     * @param gyroscopeData List of gyroscope data arrays.
     * @return List of fused sensor data arrays.
     */
    fun fuseSensorData(accelerometerData: List<FloatArray>, gyroscopeData: List<FloatArray>): List<FloatArray> {
        val fusedData = mutableListOf<FloatArray>() // List to hold fused data.

        for (i in accelerometerData.indices) {
            val accData = accelerometerData[i]
            val gyroData = gyroscopeData.getOrNull(i) ?: floatArrayOf(accData[0], 0f, 0f, 0f) // Get gyroscope data or default.

            val measurement = floatArrayOf(
                accData[0],
                accData[1] + gyroData[1],
                accData[2] + gyroData[2],
                accData[3] + gyroData[3]
            )

            fusedData.add(kalmanFilter.update(measurement)) // Update and add fused data using Kalman filter.
        }

        return fusedData
    }

    /**
     * Inner class representing a simple Kalman filter for sensor data fusion.
     */
    class KalmanFilter {
        private var stateEstimate = FloatArray(4) { 0f } // Initial state estimate.
        private var errorCovariance = Array(4) { FloatArray(4) { 0f } } // Initial error covariance matrix.
        private val processNoiseCovariance = Array(4) { FloatArray(4) { 0f } } // Process noise covariance matrix.
        private val measurementNoiseCovariance = Array(4) { FloatArray(4) { 0f } } // Measurement noise covariance matrix.
        private val identityMatrix = Array(4) { FloatArray(4) { 0f } } // Identity matrix.

        init {
            // Initialize the error covariance matrix and identity matrix
            for (i in 0..3) {
                errorCovariance[i][i] = 1f
                identityMatrix[i][i] = 1f
            }

            // Initialize the process noise covariance matrix
            val processNoise = 0.1f
            for (i in 0..3) {
                processNoiseCovariance[i][i] = processNoise
            }

            // Initialize the measurement noise covariance matrix
            val measurementNoise = 1f
            for (i in 0..3) {
                measurementNoiseCovariance[i][i] = measurementNoise
            }
        }

        fun update(measurement: FloatArray): FloatArray {
            // Prediction step
            // (In this simple example, we assume a static model, so no prediction update is required)

            // Update step
            val measurementResidual = measurement.zip(stateEstimate) { m, e -> m - e }.toFloatArray()
            val kalmanGain = calculateKalmanGain()

            // Update state estimate
            for (i in stateEstimate.indices) {
                stateEstimate[i] += kalmanGain[i] * measurementResidual[i]
            }

            // Update error covariance
            val temp = Array(4) { FloatArray(4) { 0f } }
            for (i in 0..3) {
                for (j in 0..3) {
                    temp[i][j] = identityMatrix[i][j] - kalmanGain[i] * errorCovariance[i][j]
                }
            }

            val updatedErrorCovariance = Array(4) { FloatArray(4) { 0f } }
            for (i in 0..3) {
                for (j in 0..3) {
                    updatedErrorCovariance[i][j] = 0f
                    for (k in 0..3) {
                        updatedErrorCovariance[i][j] += temp[i][k] * errorCovariance[k][j]
                    }
                    updatedErrorCovariance[i][j] += processNoiseCovariance[i][j]
                }
            }
            errorCovariance = updatedErrorCovariance

            return stateEstimate
        }

        private fun calculateKalmanGain(): FloatArray {
            val kalmanGain = FloatArray(4) { 0f }
            for (i in 0..3) {
                kalmanGain[i] = errorCovariance[i][i] / (errorCovariance[i][i] + measurementNoiseCovariance[i][i])
            }
            return kalmanGain
        }
    }

}
