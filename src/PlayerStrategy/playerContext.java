package PlayerStrategy;

public class playerContext {
    private Strategy strategy;

    public playerContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void Reinforcement() {
        this.strategy.Reinforcement();
    }


    public void Attack() {
        this.strategy.Attack();
    }


    public void Fortification() {
        this.strategy.Fortification();
    }
}
