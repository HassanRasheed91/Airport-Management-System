import java.util.ArrayList;
import java.util.List;

public class ManageCargo {
    private List<Cargo> cargoList;

    public ManageCargo() {
        this.cargoList = new ArrayList<>();
    }

    public void addCargo(Cargo cargo) {
        cargoList.add(cargo);
    }

    public void removeCargo(Cargo cargo) {
        cargoList.remove(cargo);
    }

    public double getTotalWeight() {
        double totalWeight = 0;
        for (Cargo cargo : cargoList) {
            totalWeight += cargo.getWeight();
        }
        return totalWeight;
    }

    public int getCargoCount() {
        return cargoList.size();
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }
}
