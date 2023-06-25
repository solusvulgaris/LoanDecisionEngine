# LoanDecisionEngine
Loan Decision Engine Spring Boot with a single API endpoint.

The idea of the decision engine is to determine what would be the maximum sum, 
regardless of the person requested loan amount.

If a suitable loan amount is not found within the selected period, 
the decision engine should also try to find a new suitable period.

If a person has debt then we do not approve any amount. 
If a person has no debt then we take the identifier and 
use it for calculating person's credit score taking into account the requested input.
