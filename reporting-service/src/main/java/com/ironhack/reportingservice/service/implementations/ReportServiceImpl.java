package com.ironhack.reportingservice.service.implementations;

import com.ironhack.reportingservice.enums.Status;
import com.ironhack.reportingservice.enums.UserType;
import com.ironhack.reportingservice.model.User;
import com.ironhack.reportingservice.repository.AccountRepository;
import com.ironhack.reportingservice.repository.LeadRepository;
import com.ironhack.reportingservice.repository.OpportunityRepository;
import com.ironhack.reportingservice.repository.UserRepository;
import com.ironhack.reportingservice.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
   @Autowired
   private UserRepository userRepository;

   @Autowired
   private OpportunityRepository opportunityRepository;

   @Autowired
   private AccountRepository accountRepository;

   @Autowired
   private LeadRepository leadRepository;


    public void printHeaderReportOpportunityByCity() {

        String product = String.format("%-25s", "CITY");
        String total = String.format("%-10s", "TOTAL");

        System.out.println((product + total).trim());
    }

    public static void printHeaderReportOpportunityByIndustry() {

        String product = String.format("%-25s", "INDUSTRY");
        String total = String.format("%-10s", "TOTAL");

        System.out.println((product + total).trim());
    }

    public static void printHeaderReportOpportunityByCountry() {

        String product = String.format("%-25s", "COUNTRY");
        String total = String.format("%-10s", "TOTAL");

        System.out.println((product + total).trim());
    }

    public static void printHeaderReportOpportunityByProduct() {

        String product = String.format("%-25s", "PRODUCT");
        String total = String.format("%-10s", "TOTAL");

        System.out.println((product + total).trim());
    }

    public void printHeaderOpportunitiesClosedWon() {

        String nameTitle = String.format("%-25s", "NAME");
        String totalLeadsTitle = String.format("%-10s", "TOTAL OPPORTUNITIES CLOSED_WON");

        System.out.println((nameTitle + totalLeadsTitle).trim());
    }

    public void printHeaderOpportunitiesClosedLost() {

        String nameTitle = String.format("%-25s", "NAME");
        String totalLeadsTitle = String.format("%-10s", "TOTAL OPPORTUNITIES CLOSED_LOST");

        System.out.println((nameTitle + totalLeadsTitle).trim());
    }

    public void printHeaderOpportunitiesOpen() {

        String nameTitle = String.format("%-25s", "NAME");
        String totalLeadsTitle = String.format("%-10s", "TOTAL OPPORTUNITIES OPEN");

        System.out.println((nameTitle + totalLeadsTitle).trim());
    }

    public static void printReportLeadBySalesRep() {

        String nameTitle = String.format("%-25s", "NAME");
        String totalLeadsTitle = String.format("%-10s", "TOTAL LEADS");

        System.out.println((nameTitle + totalLeadsTitle).trim());
    }


    @Override
    public String reportClosedWonByCity() {
        String closedWonByCity = "";
        if (accountRepository.countOppsByStatusAndCity(Status.CLOSED_WON).isEmpty()) {
            closedWonByCity = "There are not opportunities close-won in system";
        } else {

            printHeaderReportOpportunityByCity();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCity(Status.CLOSED_WON);

            for (int i = 0; i < objectList.size(); i++) {
                String city = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedWonByCity = closedWonByCity + city + " has " + total + " CLOSED_WON Opportunities\n";
            }
        }
        return closedWonByCity;
    }

    @Override
    public String reportCloseLostByCity() {
        String closedLostByCity = "";
        if (accountRepository.countOppsByStatusAndCity(Status.CLOSED_LOST).isEmpty()) {
            closedLostByCity = "There are not opportunities close-lost in system";
        } else {

            printHeaderReportOpportunityByCity();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCity(Status.CLOSED_LOST);

            for (int i = 0; i < objectList.size(); i++) {
                String city = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedLostByCity = closedLostByCity + city + " has " + total + " CLOSED_LOST Opportunities\n";
            }
        }
        return closedLostByCity;
    }

    @Override
    public String reportOpenByCity() {
        String openByCity = "";
        if (accountRepository.countOppsByStatusAndCity(Status.OPEN).isEmpty()) {
            openByCity = "There are not opportunities open in system";
        } else {

            printHeaderReportOpportunityByCity();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCity(Status.OPEN);

            for (int i = 0; i < objectList.size(); i++) {
                String city = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                openByCity = openByCity + city + " has " + total + " OPEN Opportunities\n";
            }
        }
        return openByCity;
    }

    @Override
    public String reportClosedWonBySalesRep() {
        String closedWonBySalesRep = "";
        if (userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.CLOSED_WON).isEmpty()) {
            closedWonBySalesRep = "There are not leads with close-won opportunities in system";
        } else {
            printHeaderOpportunitiesClosedWon();
            List<User> userList = userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.CLOSED_WON);
            for (User user : userList) {

                String name = String.format("%-25s", user.getName());
                String totalOpportunities = String.format("%-10s", user.getOpportunityList().size());

                closedWonBySalesRep = closedWonBySalesRep + name + " has " + totalOpportunities + " CLOSED_WON Opportunities\n";
            }
        }
        return closedWonBySalesRep;
    }

    @Override
    public String reportCloseLostBySalesRep() {
        String closedLostBySalesRep = "";
        if (userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.CLOSED_LOST).isEmpty()) {
            closedLostBySalesRep = "There are not leads with close-lost opportunities in system";
        } else {
            printHeaderOpportunitiesClosedLost();
            List<User> userList = userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.CLOSED_LOST);
            for (User user : userList) {

                String name = String.format("%-25s", user.getName());
                String totalOpportunities = String.format("%-10s", user.getOpportunityList().size());

                closedLostBySalesRep = closedLostBySalesRep + name + " has " + totalOpportunities + " CLOSED_LOST Opportunities\n";
            }
        }
        return closedLostBySalesRep;
    }

    @Override
    public String reportOpenBySalesRep() {
        String openBySalesRep = "";
        if (userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.OPEN).isEmpty()) {
            openBySalesRep = "There are not leads with open opportunities in system";
        } else {
            printHeaderOpportunitiesOpen();
            List<User> userList = userRepository.getAllUserWithOpportunityByUserTypeAndStatus(UserType.SALESREPRESENTATIVE, Status.OPEN);
            for (User user : userList) {
                String name = String.format("%-25s", user.getName());
                String totalOpportunities = String.format("%-10s", user.getOpportunityList().size());

                openBySalesRep = openBySalesRep + name + " has " + totalOpportunities + " OPEN Opportunities\n";
            }
        }
        return openBySalesRep;
    }

    @Override
    public String reportClosedWonByProduct() {
        String closedWonByProduct = "";
        if (accountRepository.countOppsByStatusAndProduct(Status.CLOSED_WON).isEmpty()) {
            closedWonByProduct = "There are not opportunities close-won in system";
        } else {

            printHeaderReportOpportunityByProduct();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndProduct(Status.CLOSED_WON);

            for (int i = 0; i < objectList.size(); i++) {
                String product = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedWonByProduct = closedWonByProduct + product + " has " + total + " CLOSED_WON Opportunities\n";
            }
        }
        return closedWonByProduct;
    }

    @Override
    public String reportCloseLostByProduct() {
        String closedLostByProduct = "";
        if (accountRepository.countOppsByStatusAndProduct(Status.CLOSED_LOST).isEmpty()) {
            closedLostByProduct = "There are not opportunities close-lost in system";
        } else {

            printHeaderReportOpportunityByProduct();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndProduct(Status.CLOSED_LOST);

            for (int i = 0; i < objectList.size(); i++) {
                String product = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedLostByProduct = closedLostByProduct + product + " has " + total + " CLOSED_LOST Opportunities\n";
            }
        }
        return closedLostByProduct;
    }

    @Override
    public String reportOpenByProduct() {
        String openByProduct = "";
        if (accountRepository.countOppsByStatusAndProduct(Status.OPEN).isEmpty()) {
            openByProduct = "There are not opportunities open in system";
        } else {

            printHeaderReportOpportunityByProduct();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndProduct(Status.OPEN);

            for (int i = 0; i < objectList.size(); i++) {
                String product = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                openByProduct = openByProduct + product + " has " + total + " OPEN Opportunities\n";
            }
        }
        return openByProduct;
    }

    @Override
    public String reportClosedWonByCountry() {
        String closedWonByCountry = "";
        if (accountRepository.countOppsByStatusAndCountry(Status.CLOSED_WON).isEmpty()) {
            closedWonByCountry = "There are not opportunities close-won in system";
        } else {

            printHeaderReportOpportunityByCountry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCountry(Status.CLOSED_WON);

            for (int i = 0; i < objectList.size(); i++) {
                String country = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedWonByCountry = closedWonByCountry + country + " has " + total + " CLOSED_WON Opportunities\n";
            }
        }
        return closedWonByCountry;
    }

    @Override
    public String reportCloseLostByCountry() {
        String closedLostByCountry = "";
        if (accountRepository.countOppsByStatusAndCountry(Status.CLOSED_LOST).isEmpty()) {
            closedLostByCountry = "There are not opportunities close-lost in system";
        } else {

            printHeaderReportOpportunityByCountry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCountry(Status.CLOSED_LOST);

            for (int i = 0; i < objectList.size(); i++) {
                String country = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedLostByCountry = closedLostByCountry + country + " has " + total + " CLOSED_LOST Opportunities\n";
            }
        }
        return closedLostByCountry;
    }

    @Override
    public String reportOpenByCountry() {
        String openByCountry = "";
        if (accountRepository.countOppsByStatusAndCountry(Status.OPEN).isEmpty()) {
           openByCountry  = "There are not opportunities open in system";
        } else {

            printHeaderReportOpportunityByCountry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndCountry(Status.OPEN);

            for (int i = 0; i < objectList.size(); i++) {
                String country = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                openByCountry = openByCountry + country + " has " + total + " OPEN Opportunities\n";
            }
        }
        return openByCountry;
    }

    @Override
    public String reportClosedWonByIndustry() {
        String closedWonByIndustry = "";
        if (accountRepository.countOppsByStatusAndIndustry(Status.CLOSED_WON).isEmpty()) {
           closedWonByIndustry  = "There are not opportunities close-won in system";
        } else {

            printHeaderReportOpportunityByIndustry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndIndustry(Status.CLOSED_WON);

            for (int i = 0; i < objectList.size(); i++) {
                String industry = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedWonByIndustry = closedWonByIndustry + industry + " has " + total + " CLOSED_WON Opportunities\n";
            }
        }
        return closedWonByIndustry;
    }

    @Override
    public String reportCloseLostByIndustry() {
        String closedLostByIndustry = "";
        if (accountRepository.countOppsByStatusAndIndustry(Status.CLOSED_LOST).isEmpty()) {
           closedLostByIndustry = "There are not opportunities close-lost in system";
        } else {

            printHeaderReportOpportunityByIndustry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndIndustry(Status.CLOSED_LOST);

            for (int i = 0; i < objectList.size(); i++) {
                String industry = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                closedLostByIndustry = closedLostByIndustry + industry + " has " + total + " CLOSED_LOST Opportunities\n";
            }
        }
        return closedLostByIndustry;
    }

    @Override
    public String reportOpenByIndustry() {
        String openByIndustry = "";
        if (accountRepository.countOppsByStatusAndIndustry(Status.OPEN).isEmpty()) {
           openByIndustry = "There are not opportunities open in system";
        } else {

            printHeaderReportOpportunityByIndustry();
            List<Object[]> objectList = accountRepository.countOppsByStatusAndIndustry(Status.OPEN);

            for (int i = 0; i < objectList.size(); i++) {
                String industry = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                openByIndustry = openByIndustry + industry + " has " + total + " OPEN Opportunities\n";
            }
        }
        return openByIndustry;
    }

    @Override
    public String reportOpportunityByProduct() {
        String opportunityByProduct = "";
        if (accountRepository.countOppsByProduct().isEmpty()) {
            opportunityByProduct = "There are not opportunities in system";
        } else {

            printHeaderReportOpportunityByProduct();
            List<Object[]> objectList = accountRepository.countOppsByProduct();

            for (int i = 0; i < objectList.size(); i++) {
                String product = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                opportunityByProduct = opportunityByProduct + product + " has " + total + "Opportunities\n";
            }
        }
        return opportunityByProduct;
    }

    @Override
    public String reportOpportunityByCountry() {
        String opportunityByCountry = "";
        if (accountRepository.countOppsByCountry().isEmpty()) {
            opportunityByCountry = "There are not opportunities in system";
        } else {

            printHeaderReportOpportunityByCountry();
            List<Object[]> objectList = accountRepository.countOppsByCountry();

            for (int i = 0; i < objectList.size(); i++) {
                String country = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                opportunityByCountry = opportunityByCountry + country + " has " + total + "Opportunities\n";
            }
        }
        return opportunityByCountry;
    }

    @Override
    public String reportOpportunityByCity() {
        String opportunityByCity = "";
        if (accountRepository.countOppsByCity().isEmpty()) {
            opportunityByCity = "There are not opportunities in system";
        } else {

            printHeaderReportOpportunityByCity();
            List<Object[]> objectList = accountRepository.countOppsByCity();

            for (int i = 0; i < objectList.size(); i++) {
                String city = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                opportunityByCity = opportunityByCity + city + " has " + total + "Opportunities\n";
            }
        }
        return opportunityByCity;
    }

    @Override
    public String reportOpportunityIndustry() {
        String opportunityByIndustry = "";
        if (accountRepository.countOppsByIndustry().isEmpty()) {
            opportunityByIndustry = "There are not opportunities in system";
        } else {

            printHeaderReportOpportunityByIndustry();
            List<Object[]> objectList = accountRepository.countOppsByIndustry();

            for (int i = 0; i < objectList.size(); i++) {
                String industry = String.format("%-25s", objectList.get(i)[0].toString());
                String total = String.format("%-10s", objectList.get(i)[1].toString());

                opportunityByIndustry = opportunityByIndustry + industry + " has " + total + "Opportunities\n";
            }
        }
        return opportunityByIndustry;
    }

    @Override
    public String reportOpportunityBySalesRep() {
        String opportunityBySalesRep = "";
        if (userRepository.getAllUserWithOpportunityByUserType(UserType.SALESREPRESENTATIVE).isEmpty()) {
            opportunityBySalesRep = "There are not leads in system";
        } else {
            printReportLeadBySalesRep();
            List<User> userList = userRepository.getAllUserWithOpportunityByUserType(UserType.SALESREPRESENTATIVE);
            for (User user : userList) {

                String name = String.format("%-25s", user.getName());
                String totalOpportunities = String.format("%-10s", user.getOpportunityList().size());

                opportunityBySalesRep = opportunityBySalesRep + name + " has " + totalOpportunities + "Opportunities\n";
            }
        }
        return opportunityBySalesRep;
    }

    @Override
    public String reportLeadBySalesRep() {
        String leadBySalesRep = "";
        if (leadRepository.getCountOfLeadsBySalesRep().isEmpty()) {
            leadBySalesRep = "There are not leads in system";
        } else {
            printReportLeadBySalesRep();
            List<Object[]> objectList = leadRepository.getCountOfLeadsBySalesRep();

            for (int i = 0; i < objectList.size(); i++) {
                String name = String.format("%-25s", objectList.get(i)[0].toString());
                String totalLeads = String.format("%-10s", objectList.get(i)[1].toString());

                leadBySalesRep = leadBySalesRep + name + " has " + totalLeads + "Leads\n";
            }
        }
        return leadBySalesRep;
    }

    @Override
    public String reportMeanOppsAccount() {
        String meanOppsAccount = "";

        if (opportunityRepository.meanOpportunitiesByAccount() == null) {
            meanOppsAccount = "There are not opportunities in any Account";
        } else {
            meanOppsAccount = "Mean Opportunities per Account is: " + opportunityRepository.meanOpportunitiesByAccount();
        }

        return meanOppsAccount;
    }

    @Override
    public String reportMedianOppsAccount() {
        String medianOppsAccount = "";

        if (opportunityRepository.medianOpportunitiesByAccount() == null) {
            medianOppsAccount = "There are not opportunities in any Account";
        } else {
            medianOppsAccount = "Median Opportunities per Account is: " + opportunityRepository.medianOpportunitiesByAccount();
        }

        return medianOppsAccount;
    }

    @Override
    public String reportMaxOppsAccount() {
        String maxOppsAccount = "";

        if (opportunityRepository.maxOpportunitiesByAccount() == null) {
            maxOppsAccount = "There are not opportunities in any Account";
        } else {
            maxOppsAccount = "Max Opportunities per Account is: " + opportunityRepository.maxOpportunitiesByAccount();
        }

        return maxOppsAccount;
    }

    @Override
    public String reportMinOppsAccount() {
        String minOppsAccount = "";

        if (opportunityRepository.minOpportunitiesByAccount() == null) {
            minOppsAccount = "There are not opportunities in any Account";
        } else {
            minOppsAccount = "Min Opportunities per Account is: " + opportunityRepository.minOpportunitiesByAccount();
        }

        return minOppsAccount;
    }
}
