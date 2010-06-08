package test.app;

public class WorkoutSet {
    private int userId;
    private int exerciseId;
    private int weight;
    private int iterations;
    private int setNumber;
    
    public WorkoutSet(int userId, int exerciseId, int weight, int iterations, int setNumber) {
    	this.userId = userId;
    	this.exerciseId = exerciseId;
    	this.weight = weight;
    	this.iterations = iterations;
    	this.setNumber = setNumber;
    	// Date?
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getIterations() {
		return iterations;
	}

	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

	public int getSetNumber() {
		return setNumber;
	}
}
