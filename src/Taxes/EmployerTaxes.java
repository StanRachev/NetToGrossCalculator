package Taxes;

public class EmployerTaxes extends Taxes {

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