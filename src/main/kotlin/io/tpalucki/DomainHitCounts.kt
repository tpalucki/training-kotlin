package io.tpalucki

/**
 * You are given a count-paired domain list where each element represents the total visits to a specific domain.
 * A domain like "mail.yahoo.com" consists of:
 * The full domain: "mail.yahoo.com"
 * The parent domain: "yahoo.com"
 * The top-level domain (TLD): "com"
 * When visiting "mail.yahoo.com", we implicitly visit "yahoo.com" and "com" as well.
 *
 *
 */
class DomainHitCounts {
    // Write a function subdomainVisits that takes an array of count-paired domain strings
    // (e.g., "900 google.mail.com") and returns an array/list of count-paired domains representing
    // the aggregated visit counts for every domain and subdomain.
    fun subdomainVisits(cpdomains: Array<String>): List<String> {
        // separate count / domain by " "
        // count to integer()
        // split domain by "."
        // for each subdomain add count in map

        val domainsCount = mutableMapOf<String, Int>()

        for (cpdomain in cpdomains) {
            val countAndDomainParts = cpdomain.split(" ")
            val count = countAndDomainParts[0].toInt()
            val domain = countAndDomainParts[1]

            val sumdomainsParts = domain.split(".")

            // mail.google.com
            // google.com
            // com
            var currentDomain = domain
            while (currentDomain.isNotEmpty()) {
                domainsCount[currentDomain] = domainsCount.getOrDefault(currentDomain, 0) + count

                val firstDotIndex = currentDomain.indexOf(".")
                if (firstDotIndex == -1) {
                    break
                } else {
                    currentDomain = currentDomain.substring(firstDotIndex + 1)
                }
            }
        }
        return domainsCount.map { (domain, count) -> "$count $domain" }
    }
}
