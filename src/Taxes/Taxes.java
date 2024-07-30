package Taxes;

public abstract class Taxes {

    protected final float salaryNet;
    protected final float tTIPercent; // ДОД - Данък върху Общ Доход
    protected final float tTIBGN;
    protected final float pensionFundPercentage;
    protected final float unemploymentFundPercentage;
    protected final float healthInsurancePercentage;
    protected final float gIMPercentage; // ОЗМ - Общо Заболяване и Майчинство
    protected final float aCPIPercentage; // ДЗПО - Допълнително Задължително Пенсионно Осигуряване
    private final float insuranceThreshold;
    protected float insuranceAmount;

    public Taxes (float salaryNet, float pensionFundPercentage, float unemploymentFundPercentage,
                  float healthInsurancePercentage, float gIMPercentage, float aCPIPercentage) {

        this.salaryNet = salaryNet;
        this.tTIPercent = 0.1F;
        this.tTIBGN = (salaryNet * tTIPercent) / 0.9F; // formula
        this.pensionFundPercentage = pensionFundPercentage;
        this.unemploymentFundPercentage = unemploymentFundPercentage;
        this.healthInsurancePercentage = healthInsurancePercentage;
        this.gIMPercentage = gIMPercentage;
        this.aCPIPercentage = aCPIPercentage;
        this.insuranceThreshold = 3400F;
        this.insuranceAmount = calculateInsuranceAmount();
    }

    private float calculateInsuranceAmount() {
        return (salaryNet >= (insuranceThreshold * 0.8622F) * 0.9F ? insuranceThreshold : (salaryNet + tTIBGN) / 0.8622F);
    }                        // formula                                                   // formula

    protected float percentageToBGN(float tax) {
        return (tax / 100F) * insuranceAmount;
    }

    public abstract float totalTaxesBGN();
    public abstract String printListTaxes();
}