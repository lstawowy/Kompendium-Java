public class Player {
private Integer value;
private Sign sign = Sign.O;
private Boolean AIEnabled = Boolean.FALSE;

public Player(Integer value, Sign sign) {
	this.value = value;
	this.sign = sign;
}

public Player(Integer value, Sign sign, Boolean AiEnabled) {
	this.value = value;
	this.sign = sign;
	this.AIEnabled = AiEnabled;
}

public Sign getSign() {
	return sign;
}

public void setSign(Sign sign) {
	this.sign = sign;
}

public Integer getValue() {
	return value;
}

public void setValue(Integer value) {
	this.value = value;
}

public Boolean getAIEnabled() {
	return AIEnabled;
}

public void setAIEnabled(Boolean AIEnabled) {
	this.AIEnabled = AIEnabled;
}

@Override
public String toString() {
	if (!AIEnabled) {
		return "Player{" +
				"value=" + value +
				", sign=" + sign +
				'}';
	} else {
		return "AI{" +
				"value=" + value +
				", sign=" + sign +
				'}';
	}
}

}
