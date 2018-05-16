
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.Instructor;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	//Managed repository

	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Supporting services

	@Autowired
	private ActorService				actorService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//Simple CRUD methods

	public Curriculum create() {
		final Curriculum curriculum = new Curriculum();

		final Instructor instructor = (Instructor) this.actorService.findByPrincipal();
		curriculum.setInstructor(instructor);
		curriculum.setTicker(this.generateTicker());
		curriculum.setEducationRecord(new ArrayList<EducationRecord>());
		curriculum.setEndorserRecord(new ArrayList<EndorserRecord>());
		curriculum.setMiscellaneousRecord(new ArrayList<MiscellaneousRecord>());
		curriculum.setProfessionalRecord(new ArrayList<ProfessionalRecord>());

		return curriculum;
	}

	public Curriculum findOne(final int id) {
		Assert.notNull(id);

		return this.curriculumRepository.findOne(id);
	}

	public Collection<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum save(final Curriculum c) {
		Assert.notNull(c);

		//Assertion that the user modifying this curriculum has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getInstructor().getId());

		final Curriculum saved = this.curriculumRepository.save(c);

		return saved;
	}

	public void delete(final Curriculum c) {
		Assert.notNull(c);

		//Assertion that the user deleting this curriculum has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getInstructor().getId());

		if (c.getPersonalRecord().getName() != null)
			this.personalRecordService.delete(c.getPersonalRecord());

		if (!(c.getEducationRecord().isEmpty()))
			for (final EducationRecord er : c.getEducationRecord())
				this.educationRecordService.delete(er);

		if (!(c.getEndorserRecord().isEmpty()))
			for (final EndorserRecord er : c.getEndorserRecord())
				this.endorserRecordService.delete(er);

		if (!(c.getProfessionalRecord().isEmpty()))
			for (final ProfessionalRecord pr : c.getProfessionalRecord())
				this.professionalRecordService.delete(pr);

		if (!(c.getMiscellaneousRecord().isEmpty()))
			for (final MiscellaneousRecord mr : c.getMiscellaneousRecord())
				this.miscellaneousRecordService.delete(mr);

		this.curriculumRepository.delete(c);
	}

	//Other methods

	//Generates the first half of the unique tickers.
	private String generateNumber() {
		final Date date = new Date();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String año = campos[0].trim().substring(2, 4);
		final String mes = campos[1].trim();
		final String dia = campos[2].trim();

		final String res = año + mes + dia;
		return res;
	}

	//Generates the second half of the unique tickers.
	private String generateString() {
		String cadenaAleatoria = "";
		final int longitud = 4;
		final Random r = new Random();
		int i = 0;
		while (i < longitud) {
			final int rnd = r.nextInt(255);
			final char c = (char) (rnd);
			if ((c >= 'A' && c <= 'z' && Character.isLetter(c))) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker() {
		final String res = this.generateNumber() + "-" + this.generateString();
		return res;
	}
}
