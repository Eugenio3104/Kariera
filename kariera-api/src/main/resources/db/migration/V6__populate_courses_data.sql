INSERT INTO study_areas (name) VALUES
('Area Formazione di Educatori e Insegnanti'),
('Area Ingegneria e Tecnologia'),
('Area Scienze'),
('Area Umanistica'),
('Area Medico Sanitaria'),
('Area Socio-Economica');

INSERT INTO courses (name, study_area_id) VALUES
('Scienze dell''educazione', (SELECT id FROM study_areas WHERE name = 'Area Formazione di Educatori e Insegnanti')),
('Scienze della formazione primaria', (SELECT id FROM study_areas WHERE name = 'Area Formazione di Educatori e Insegnanti'));

INSERT INTO courses (name, study_area_id) VALUES
('Informatica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria biomedica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria chimica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria civile', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria edile e architettura', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria elettronica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria gestionale', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria informatica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria meccanica', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Ingegneria per l''ambiente e la sicurezza del territorio', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia')),
('Tecnologie del mare e della navigazione', (SELECT id FROM study_areas WHERE name = 'Area Ingegneria e Tecnologia'));

INSERT INTO courses (name, study_area_id) VALUES
('Biologia', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Chimica', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Conservazione e restauro dei beni culturali', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Fisica', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Matematica', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Scienze delle tecnologie biologiche', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Scienze e tecnologie per le attività motorie', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Scienze geologiche', (SELECT id FROM study_areas WHERE name = 'Area Scienze')),
('Scienze naturali e ambientali', (SELECT id FROM study_areas WHERE name = 'Area Scienze'));

INSERT INTO courses (name, study_area_id) VALUES
('Comunicazione e dams', (SELECT id FROM study_areas WHERE name = 'Area Umanistica')),
('Lettere e storia', (SELECT id FROM study_areas WHERE name = 'Area Umanistica')),
('Lettere', (SELECT id FROM study_areas WHERE name = 'Area Umanistica')),
('Lingue e culture moderne', (SELECT id FROM study_areas WHERE name = 'Area Umanistica')),
('Mediazione linguistica', (SELECT id FROM study_areas WHERE name = 'Area Umanistica')),
('Scienze e tecnologie psicologiche', (SELECT id FROM study_areas WHERE name = 'Area Umanistica'));

INSERT INTO courses (name, study_area_id) VALUES
('Chimica e tecnologie farmaceutiche', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Farmacia', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Fisioterapia', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Igiene dentale', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Informazione scientifica del farmaco', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Medicina e Chirurgia Ed. Italiana', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria')),
('Scienze della nutrizione', (SELECT id FROM study_areas WHERE name = 'Area Medico Sanitaria'));

INSERT INTO courses (name, study_area_id) VALUES
('Economia', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Economia aziendale', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Giurisprudenza', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Media e società digitali', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Scienze del marketing', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Scienze dell''amministrazione', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Scienze politiche e relazioni internazionali', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Scienze sociali e del lavoro digitale', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Servizio sociale', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica')),
('Statistica per data science', (SELECT id FROM study_areas WHERE name = 'Area Socio-Economica'));