public abstract class Taxes {

    protected float salaryNet;
    protected float tTIPercent; // ДОД - Данък върху Общ Доход
    protected float tTIBGN;
    protected float pensionFundPercentage;
    protected float unemploymentFundPercentage;
    protected float healthInsurancePercentage;
    protected float gIMPercentage; // ОЗМ - Общо Заболяване и Майчинство
    protected float aCPIPercentage; // ДЗПО - Допълнително Задължително Пенсионно Осигуряване
    protected float insuranceThreshold;

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
        this.insuranceThreshold = calculateInsuranceThreshold();
    }

    private float calculateInsuranceThreshold() {

        if (salaryNet >= ((3400 * 0.8622F) * 0.9F)) { // formula
            return 3400;
        }

        return (salaryNet + tTIBGN) / 0.8622F; // formula
    }

    protected float percentageToBGN(float tax) {
        tax = (tax / 100) * insuranceThreshold;
        return tax;
    }

    public abstract float totalTaxesBGN();
    public abstract String printListTaxes();
    public abstract float totalTaxesPercentage();
}

class EmployeeTaxes extends Taxes {

    public EmployeeTaxes(float salaryNet) {
        super(salaryNet, 6.58F, 0.4F, 3.2F, 1.4F, 2.2F);
    }

    @Override
    public float totalTaxesBGN() {

        return (((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                  gIMPercentage + aCPIPercentage) / 100 * insuranceThreshold) + tTIBGN) ;
    }
    @Override
    public float totalTaxesPercentage() {

        return pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
               gIMPercentage + aCPIPercentage + tTIPercent;
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
                "%n%-59s%.2f%2%%12.2f лв.",
                "Удръжки Служител",
                "Фонд Пенсии", pensionFundPercentage, percentageToBGN(pensionFundPercentage),
                "Фонд Безработица", unemploymentFundPercentage, percentageToBGN(unemploymentFundPercentage),
                "Здравно Осигуряване", healthInsurancePercentage, percentageToBGN(healthInsurancePercentage),
                "Общо Заболяване и Майчинство", gIMPercentage, percentageToBGN(gIMPercentage),
                "Допълнително Задължително Пенсионно Осигуряване", aCPIPercentage, percentageToBGN(aCPIPercentage),
                "Данък върху Общ Доход", tTIPercent * 100, tTIBGN,
                "Общо", totalTaxesPercentage(), totalTaxesBGN());
    }
}

class EmployerTaxes extends Taxes {

    protected float oAOD; // РЗПБ = Трудова Злополука и Професионална Болест
    protected static float totalTaxesBGN;

    public EmployerTaxes(float salaryNet) {
        super(salaryNet, 8.22F, 0.6F, 4.8F, 2.1F, 2.8F);
        oAOD = 0.4F;
        totalTaxesBGN = totalTaxesBGN();
    }

    @Override
    public float totalTaxesBGN() {

        return ((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                 gIMPercentage + aCPIPercentage + oAOD) / 100) * insuranceThreshold;
    }

    @Override
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
                "Удръжки Работодател",
                "Фонд Пенсии", pensionFundPercentage, percentageToBGN(pensionFundPercentage),
                "Фонд Безработица", unemploymentFundPercentage, percentageToBGN(unemploymentFundPercentage),
                "Здравно Осигуряване", healthInsurancePercentage, percentageToBGN(healthInsurancePercentage),
                "Общо Заболяване и Майчинство", gIMPercentage, percentageToBGN(gIMPercentage),
                "Допълнително Задължително Пенсионно Осигуряване", aCPIPercentage, percentageToBGN(aCPIPercentage),
                "Трудова Злополука и Професионална Болест", oAOD, percentageToBGN(oAOD),
                "Общо", totalTaxesPercentage(), totalTaxesBGN());
    }
}

