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
        this.insuranceAmount = calculateInsuranceThreshold();
        this.insuranceAmount = calculateInsuranceThreshold();
    }

    private float calculateInsuranceThreshold() {
        if (salaryNet >= ((insuranceThreshold * 0.8622F) * 0.9F)) { // formula
            return insuranceThreshold;
        }
        return (salaryNet + tTIBGN) / 0.8622F; // formula
    }

    protected float percentageToBGN(float tax) {
        tax = (tax / 100) * insuranceAmount;
        return tax;
    }

    public abstract float totalTaxesBGN();
    public abstract String printListTaxes();
}

class EmployeeTaxes extends Taxes {

    public EmployeeTaxes(float salaryNet) {
        super(salaryNet, 6.58F, 0.4F, 3.2F, 1.4F, 2.2F);
    }

    public float totalTaxesBGNwithoutTTI() {
        return ((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                gIMPercentage + aCPIPercentage) / 100) * insuranceAmount;
    }

    @Override
    public float totalTaxesBGN() {
        return (((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                  gIMPercentage + aCPIPercentage) / 100) * insuranceAmount) + tTIBGN;
    }

    public float totalTaxesPercentageWithoutTTI() {
        return pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                gIMPercentage + aCPIPercentage;
    }

    public String printSalariesAndTotal() {
        return String.format(
                "%-66s%12.2f лв.%n" +
                "%-66s%12.2f лв.%n" +
                "%-66s%12.2f лв.",
                "Нетна заплата: ", salaryNet,
                "Брутна заплата: ", salaryNet + totalTaxesBGN(),
                "Общо разходи работодател: ", salaryNet + totalTaxesBGN() + EmployerTaxes.totalTaxesBGN);
    }

    @Override
    public String printListTaxes() {
        return String.format(
                "%n%s%n" +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-59s%.2f%2%%12.2f лв." +
                "%n%-59s%.2f%2%%12.2f лв." +
                "%n%-59s%19.2f лв.",
                "Удръжки Служител (Върху Осигурителен Доход)",
                "Фонд Пенсии", pensionFundPercentage, percentageToBGN(pensionFundPercentage),
                "Фонд Безработица", unemploymentFundPercentage, percentageToBGN(unemploymentFundPercentage),
                "Здравно Осигуряване", healthInsurancePercentage, percentageToBGN(healthInsurancePercentage),
                "Общо Заболяване и Майчинство", gIMPercentage, percentageToBGN(gIMPercentage),
                "Допълнително Задължително Пенсионно Осигуряване", aCPIPercentage, percentageToBGN(aCPIPercentage),
                "Общо осигурителни вноски", totalTaxesPercentageWithoutTTI(), totalTaxesBGNwithoutTTI(),
                "Данък върху Общ Доход", tTIPercent * 100, tTIBGN,
                "Общо удръжки", totalTaxesBGN());
    }
}

class EmployerTaxes extends Taxes {

    private final float oAOD; // РЗПБ = Трудова Злополука и Професионална Болест
    protected static float totalTaxesBGN;

    public EmployerTaxes(float salaryNet) {
        super(salaryNet, 8.22F, 0.6F, 4.8F, 2.1F, 2.8F);
        oAOD = 0.4F;
        totalTaxesBGN = totalTaxesBGN();
    }

    @Override
    public float totalTaxesBGN() {
        return ((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                 gIMPercentage + aCPIPercentage + oAOD) / 100F) * insuranceAmount;
    }

    public float totalTaxesPercentage() {
        return pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
               gIMPercentage + aCPIPercentage + oAOD;
    }

    @Override
    public String printListTaxes() {
        return String.format(
                "%n%s%n" +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-60s%.2f%2%%12.2f лв." +
                "%n%-59s%.2f%2%%12.2f лв.",
                "Удръжки Работодател (Върху Осигурителен Доход)",
                "Фонд Пенсии", pensionFundPercentage, percentageToBGN(pensionFundPercentage),
                "Фонд Безработица", unemploymentFundPercentage, percentageToBGN(unemploymentFundPercentage),
                "Здравно Осигуряване", healthInsurancePercentage, percentageToBGN(healthInsurancePercentage),
                "Общо Заболяване и Майчинство", gIMPercentage, percentageToBGN(gIMPercentage),
                "Допълнително Задължително Пенсионно Осигуряване", aCPIPercentage, percentageToBGN(aCPIPercentage),
                "Трудова Злополука и Професионална Болест", oAOD, percentageToBGN(oAOD),
                "Общо удръжки", totalTaxesPercentage(), totalTaxesBGN());
    }
}

