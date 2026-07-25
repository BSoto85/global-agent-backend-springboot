-- Seed data (schema is managed by schema.sql)

INSERT INTO countries(name, flag, country_code, language_code, silhouette)
VALUES
    ('Canada', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721233336/GlobalAgent-flagCanadaGif_f9bbfq.gif', 'ca', 'en', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_390,w_390/v1723473096/GlobalAgent-Canada_x9mxgc.png'),
    ('Mexico', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721589677/GlobalAgent-Mexicoflag_pat31d.gif', 'mx', 'es', 'https://res.cloudinary.com/dgifdj6nx/image/upload/v1722611809/GlobalAgent-Tile-MexicoNB_qtzvgs.webp'),
    ('Germany', 'https://res.cloudinary.com/dgifdj6nx/image/upload/c_scale,h_139,w_200/v1721419276/GlobalAgent-flag_wkgicw.gif', 'de', 'de', 'https://res.cloudinary.com/dgifdj6nx/image/upload/v1722611885/GlobalAgent-Tile-Germany_1_gazhk1.png')
ON CONFLICT DO NOTHING;
