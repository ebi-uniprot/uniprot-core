/**
 * 
 */
/**
 * @author jluo
 *
 */
module uniprot.ff.parser {
	exports uk.ac.ebi.uniprot.parser.ffwriter.impl;
	exports uk.ac.ebi.uniprot.parser.converter;
	exports uk.ac.ebi.uniprot.parser.impl.rx;
	exports uk.ac.ebi.uniprot.parser.ffwriter;
	exports uk.ac.ebi.uniprot.parser.ffwriter.line.ft;
	exports uk.ac.ebi.uniprot.parser.impl.dt;
	exports uk.ac.ebi.uniprot.parser.impl.entry;
	exports uk.ac.ebi.uniprot.parser.impl.dr;
	exports uk.ac.ebi.uniprot.parser.impl.ft;
	exports uk.ac.ebi.uniprot.parser.transformer;
	exports uk.ac.ebi.uniprot.parser.impl.rl;
	exports uk.ac.ebi.uniprot.parser.impl.pe;
	exports uk.ac.ebi.uniprot.parser.impl.rg;
	exports uk.ac.ebi.uniprot.parser.impl.de;
	exports uk.ac.ebi.uniprot.parser.impl.rt;
	exports uk.ac.ebi.uniprot.parser.impl.rn;
	exports uk.ac.ebi.uniprot.parser.impl.rp;
	exports uk.ac.ebi.uniprot.parser.impl.ox;
	exports uk.ac.ebi.uniprot.parser.integration;
	exports uk.ac.ebi.uniprot.validator;
	exports uk.ac.ebi.uniprot.parser.impl.os;
	exports uk.ac.ebi.uniprot.parser.impl;
	exports uk.ac.ebi.uniprot.parser.impl.gn;
	exports uk.ac.ebi.uniprot.parser.ffwriter.line.cc;
	exports uk.ac.ebi.uniprot.parser.impl.rc;
	exports uk.ac.ebi.uniprot.antlr;
	exports uk.ac.ebi.uniprot.parser.impl.kw;
	exports uk.ac.ebi.uniprot.parser.impl.ra;
	exports uk.ac.ebi.uniprot.parser.impl.oh;
	exports uk.ac.ebi.uniprot.parser.impl.og;
	exports uk.ac.ebi.uniprot.parser.impl.id;
	exports uk.ac.ebi.uniprot.parser;
	exports uk.ac.ebi.uniprot.parser.ffwriter.line;
	exports uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;
	exports uk.ac.ebi.uniprot.parser.impl.oc;
	exports uk.ac.ebi.uniprot.parser.impl.cc;
	exports uk.ac.ebi.uniprot.parser.impl.ss;
	exports uk.ac.ebi.uniprot.parser.impl.ac;
	exports uk.ac.ebi.uniprot.parser.impl.sq;

	requires antlr4;
	requires guava;
	requires junit;
	requires slf4j.api;
	requires uniprot.domain;
	requires validation.api;
}