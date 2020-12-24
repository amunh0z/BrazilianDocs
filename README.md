# BrazilianDocs
BrazilianDocs is a CA Fast Data Masker set of custom functions which holds the most common IDs used in Brazil. Those are:
* **CPF** is the Natural Persons Registration, that is a unique number for the whole country, and it is used as the main ID in most of the systems in the country, sometimes as a primary key in databases, and also the main information for banking, for contracts and etc. It is equivalent to the US Social Security Number. Usually, people can only have it after a certain age.
* **CNPJ** is the  equivalent to the CPF, but it is for companies.
* **RG** is the Brazilian General Registration record. Every Brazilian citizen should have this document. It is usually the Photo ID everyone has before they are able to take a driver's license.
* **CHN** is the Brazilian Driver's license. Usually, when one has it, the RG gets somewhat deprecated.
* **Titulo** is the Brazilian Voter Registration card. And it is manly used on Election days, but some places requires for mortgage and even for civil service examinations.

Those are the main IDs that we use in Brazil. And, of course, there are some more of them. All of them use the [LUHN mod 11 algorithm for checksums](https://en.wikipedia.org/wiki/Luhn_mod_N_algorithm).

## Main Features

* The set of functions will generated valid Brazilian IDs.
* The set of functions may receive parameters to define the number of digits to keep and if formatting is required.
* The set of functions use the CA Fast Datamasker IMaskFunction documented on [Create Custom Masking Functions](ttps://techdocs.broadcom.com/us/en/ca-enterprise-software/devops/test-data-management/4-9/reference/data-generation-functions-and-parameters/create-custom-masking-functions.html).

## Installing

A step by step, with examples, that tell you how to get the built function working on a CA Fast Data Masker installation.

First of all, custom functions support were added at **CA Fast Data Masker version 4.3**. So, if you have a version prior to this, stop now and update it to the last release, or this will not work at all.

Go to the FDM install folder and create a folder called `custom`.
```
Usually it's installed at C:\Program Files\Grid-Tools\FastDataMasker
```

Download a `jar` from one of the available project's [Releases](https://github.com/amunh0z/BrazilianDocs/releases) page. Add this `jar` to the `custom` folder from the previous step.

Modify the provided *custom_config.xml* and add the xml below inside the `<functions>` tag.

```xml
        <function>
            <name>CPF</name>
            <description>CPF - Generates valid Brazilian CPF</description>
            <parm1># Digits to keep (0 to 9)?</parm1>
            <parm2>Use Separators (Y/N)?</parm2>
            <char>true</char>
            <number>true</number>
            <date>false</date>
            <char_date>false</char_date>
            <custom>true</custom>
            <class_name>com.broadcom.utils.CPF</class_name>
        </function>
```

## Built With

* [Java](https://www.oracle.com/technetwork/java/index.html) - Main language used to create this function.
* [FastDataMasker](https://docops.ca.com/ca-test-data-manager) - CA proprietary Data Masker library
* [JUnit](https://junit.org/junit4/) - Testing framework

